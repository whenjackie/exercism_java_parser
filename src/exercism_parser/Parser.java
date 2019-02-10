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
    
    private static class ReturnVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(ReturnStmt returnStmt, Void args) {
        	// TODO: insert a println before the return stmt
//        	System.out.println(returnStmt.toString());
        	System.out.println("Returning: " + returnStmt.getExpression().toString());
        	super.visit(returnStmt, args);
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
//    	System.out.println("HELLO WORLD!");
//    	CompilationUnit compilationUnit = JavaParser.parse("class A { }");
//    	Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
    	
    	String code = "class HelloWorld { String helloWorld() {String x = \"Hello World!\"; return x;}}";
//    	String code = "class A { int f = 2; String g = '5'; void foo(int p) { return 'z'; }}";
    	
//    	FileInputStream in = new FileInputStream("test.java");
    	CompilationUnit cu = JavaParser.parse(code);
    	JsonPrinter printer = new JsonPrinter(true);
    	System.out.println(printer.output(cu));

//    	System.out.println(cu);
    	cu.accept(new MethodVisitor(), null);
    	cu.accept(new ReturnVisitor(), null);
//    	cu.accept(new VariableVisitor(), null);
    	List<String> variableNames = new ArrayList<String>();
    	VoidVisitor<List<String>> variableNameCollector = new VariableVisitor();
    	variableNameCollector.visit(cu, variableNames);
    	variableNames.forEach(n -> System.out.println("Variable Collected: " + n));
    	
//    	process(cu.findRootNode());


    }
}


