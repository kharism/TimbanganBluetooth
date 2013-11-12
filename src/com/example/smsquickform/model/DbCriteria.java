package com.example.smsquickform.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DbCriteria {
	String where="";
	String order;
	String having;
	String group;
	Map<String,String> colCondition;
	List<String> conditions;
	public DbCriteria(){
		colCondition = new HashMap<String, String>();
		conditions = new ArrayList<String>();
	}
	public void addColumnCondition(String colName,String colValue){
		colCondition.put(colName, colValue);
	}
	public void addCondition(String condition, String conjungtor){
		conditions.add(conjungtor+" ("+condition+")");
	}
	public void addCondition(String condition){
		addCondition(condition, "and");
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getHaving() {
		return having;
	}
	public void setHaving(String having) {
		this.having = having;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void addWhere(String condition){
		where += condition;
	}
	public String getConditions(){
		String cond = where;
		Set<Entry<String,String>> entries =colCondition.entrySet();
		Iterator<Entry<String,String>> it = entries.iterator();
		while(it.hasNext()){
			Entry<String,String> k = it.next();
			cond+=" and ("+k.getKey()+"="+k.getValue()+")";;
		}
		Iterator<String> it2 = conditions.iterator();
		while(it2.hasNext()){
			cond+=it.next();
		}
		return cond;
	}

	
}
