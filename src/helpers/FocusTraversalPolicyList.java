package helpers;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.List;

public class FocusTraversalPolicyList extends FocusTraversalPolicy {
	
	public List<Component> list;

	public FocusTraversalPolicyList() {
		this.list=new ArrayList<Component>();
	}
	
	public FocusTraversalPolicyList(List<Component> order){
		this.list=order;
	}
	
	public FocusTraversalPolicyList(Component [] order) {
		this.list=new ArrayList<Component>();
		for (int i=0;i<order.length;i++)
			this.list.add(order[i]);
	}

	@Override
	public Component getComponentAfter(Container container, Component component) {
		int index=list.indexOf(component);
		return list.get((index+1)% list.size());
	}

	@Override
	public Component getComponentBefore(Container container, Component component) {
		int index=list.indexOf(component);
		return list.get((index-1+list.size())% list.size());
	}

	@Override
	public Component getDefaultComponent(Container container) {
		return list.get(0);
	}

	@Override
	public Component getFirstComponent(Container container) {
		return list.get(0);
	}

	@Override
	public Component getLastComponent(Container container) {
		return list.get(list.size()-1);
	}

}
