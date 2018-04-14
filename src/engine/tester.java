package engine;

import engine.entity.Row;

public class tester {

	public static void main(String[] args) {
		Database d = Database.getInstance();
		d.insertIntoMovie(new Row("title", "producer", "mainactor", 0, 0, "media", true, true, 1, "picture.png"));
	}

}
