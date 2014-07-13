package com.janith.storm;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class PackagesResource {
	AnnotationDB db = null;
	public PackagesResource (Class clazz) {
		//URL url = ClasspathUrlFinder.findClassPaths(arg0)
		 URL url = ClasspathUrlFinder.findClassBase(clazz);
		 db = new AnnotationDB();
		
		try {
			db.scanArchives(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set<String> getAnnotationClasses(){
		return db.getAnnotationIndex().get(Stomp.class.getName());
	}
	
	
}
