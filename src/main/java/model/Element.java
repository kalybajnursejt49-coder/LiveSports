package model;
import visitor.Visitor;
public interface Element { <R> R accept(Visitor<R> visitor); }
