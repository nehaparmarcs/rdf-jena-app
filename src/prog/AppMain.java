package prog;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;


public class AppMain {
	public static void main(String[] args) {

		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("prog/data.rdf");
		
		model.write(System.out,"TURTLE");
//		model.write(System.out,"RDF/JSON");
		
		String queryString =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"SELECT * WHERE { " +
				"?person foaf:name ?x ."+
				"}";
		
		
		String grandParentsString =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"SELECT * WHERE { " +
				"?person foaf:name ?x ."+
				"?person foaf:grandparentOf ?y ."+
				"FILTER(?y = \"Guddu\")" +
				"}";
		
		
		String queryStringNotMarried =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"SELECT * WHERE { " +
				"?person foaf:name ?x ."+
				"?person foaf:married ?y ."+
				"FILTER(?y = \"no\")" +
				"}";
		
		
		String queryStringSiblings =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"SELECT * WHERE { " +
				"?person foaf:name ?x ."+
				"?person foaf:hasSibling ?y ."+
				"FILTER(?y = \"yes\")" +
				"}";
		
		
		Query query = QueryFactory.create(queryString);
		Query grandParentsQuery = QueryFactory.create(grandParentsString);
		Query query1 = QueryFactory.create(queryStringNotMarried);
		Query query2 = QueryFactory.create(queryStringSiblings);
		
		QueryExecution qexec = QueryExecutionFactory.create(query,model);
		QueryExecution qexecGrandParents = QueryExecutionFactory.create(grandParentsQuery,model);
		QueryExecution qexec1 = QueryExecutionFactory.create(query1,model);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,model);

		ResultSet results = ((QueryExecution) qexec).execSelect();
		System.out.println("\n\nAll members in the Family tree. \n");
		while( results.hasNext())
		{
			QuerySolution soln = results.nextSolution();
			Literal name = soln.getLiteral("x");
			System.out.println(name);
		}
		System.out.println("\n\n");
		
	
		results = ((QueryExecution) qexec1).execSelect();
		System.out.println("Who all are unmarried in the family??\n");
		while( results.hasNext())
		{
			QuerySolution soln = results.nextSolution();
			Literal name = soln.getLiteral("x");
			System.out.println(name);
		}
		System.out.println("\n\n");
		
		
		
		
		results = ((QueryExecution) qexecGrandParents).execSelect();
		System.out.println("Who are the grandParents in the family??\n");
		while( results.hasNext())
		{
			QuerySolution soln = results.nextSolution();
			Literal name = soln.getLiteral("x");
			System.out.println(name);
		}
		System.out.println("\n\n");
		
		
		
		results = ((QueryExecution) qexec2).execSelect();
		System.out.println("Who all are Siblings ??\n");
		while( results.hasNext())
		{
			QuerySolution soln = results.nextSolution();
			//Resource rs = soln.getResource("x");
			//RDFNode node = soln.get("x")
			Literal name = soln.getLiteral("x");
			System.out.println(name);

		}
		System.out.println("\n\n\n");
		
	
		
		
	}
}