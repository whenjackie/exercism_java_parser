package exercism_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
//import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.observer.AstObserver;
import com.github.javaparser.ast.observer.AstObserverAdapter;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.TreeVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.JsonPrinter;

public class Parser {
	static void process(Node node){
	    // Do something with the node
	    for (Node child : node.getChildNodes()){
	    	System.out.println(child);
	        process(child);
	    }
	}
    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this 
             CompilationUnit, including inner class methods */
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
    
    private static class ReturnVisitor extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(ReturnStmt returnStmt, List<String> returnedVal) {
        	// TODO: insert a println before the return stmt
//        	System.out.println(returnStmt.toString());
        	System.out.println("Returning: " + returnStmt.getExpression().toString());
        	returnedVal.add(returnStmt.getExpression().toString());
        	super.visit(returnStmt, returnedVal);
//        	return returnStmt;
        }
    }
    
//    private static class VariableVisitor extends VoidVisitorAdapter<List<String>> {
//        @Override
//        public void visit(FieldDeclaration variable, List<String> collector) {
//        	// TODO: insert a println before the return stmt
////        	System.out.println(variable.toString());
//        	System.out.println(variable.toString());
//        	super.visit(variable, collector);
//        	collector.add(variable.toString());
////        	return returnStmt;
//        }
//    }
    
    private static class VariableVisitor extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(VariableDeclarator variable, List<String> collector) {
        	// TODO: insert a println before the return stmt
//        	System.out.println(variable.toString());
        	super.visit(variable, collector);
        	collector.add(variable.toString());
//        	return returnStmt;
        }
    }
    
    
    public static void main(String [] Args){
//    	Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
    	
    	String code = "class HelloWorld { String helloWorld() {String x = \"Hello World!\"; return x;}}";
//    	String code = "class A { int f = 2; String g = '5'; void foo(int p) { return 'z'; }}";
    	
//    	FileInputStream in = new FileInputStream("test.java");
    	CompilationUnit cu = JavaParser.parse(code);
    	JsonPrinter printer = new JsonPrinter(true);
    	System.out.println(printer.output(cu));
    	
    	//Testing out method that is supposed to print all method names
    	cu.accept(new MethodVisitor(), null);
    	
    	//Collect what is being returned
    	List<String> returnedValue = new ArrayList<String>();
    	new ReturnVisitor().visit(cu, returnedValue);
    	
    	List<String> variableNames = new ArrayList<String>();
    	VoidVisitor<List<String>> variableNameCollector = new VariableVisitor();
    	variableNameCollector.visit(cu, variableNames);
    	variableNames.forEach(n -> System.out.println("Variable Collected: " + n));
    	
    	if(returnedValue.get(0).contains("Hello World!")){
    		System.out.println("Code works!");
    	}
    	//Use regex to get the variable name between brackets? Right now it appears as Optional[Variable_Name]
//    	else{
//    		
//    	}
    	

    }
}


