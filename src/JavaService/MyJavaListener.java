package JavaService;

import JavaService.Java8Parser.DoStatementContext;
import JavaService.Java8Parser.ForStatementContext;
import JavaService.Java8Parser.WhileStatementContext;
import Model.IterationData;

public class MyJavaListener extends Java8ParserBaseListener {
	
	private int loopLevel = 0;
	private IterationData data;
	 
	public MyJavaListener() {
		this.data = new IterationData();
	}

	public IterationData getData() {
		return data;
	}

	public void setData(IterationData data) {
		this.data = data;
	}

	@Override
	public void exitDoStatement(DoStatementContext ctx) {
		// TODO Auto-generated method stub
		super.exitDoStatement(ctx);
		this.loopLevel --;
	}

	@Override
	public void enterForStatement(ForStatementContext ctx) {
		// TODO Auto-generated method stub
		super.enterForStatement(ctx);
		this.data.loopCount++;
		this.loopLevel++;
		if(loopLevel >= this.data.maxLevel ) this.data.maxLevel = loopLevel;
	}

	@Override
	public void exitForStatement(ForStatementContext ctx) {
		// TODO Auto-generated method stub
		super.exitForStatement(ctx);
		this.loopLevel --;
	}

	@Override
	public void enterWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		super.enterWhileStatement(ctx);
		this.data.loopCount++;
		this.loopLevel++;
		if(loopLevel >= this.data.maxLevel ) this.data.maxLevel = loopLevel;
	}

	@Override
	public void exitWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		super.exitWhileStatement(ctx);
		this.loopLevel --;
	}

	@Override
	public void enterDoStatement(DoStatementContext ctx) {
		// TODO Auto-generated method stub
		super.enterDoStatement(ctx);
		this.data.loopCount++;
		this.loopLevel++;
		if(loopLevel >= this.data.maxLevel ) this.data.maxLevel = loopLevel;
	}
}
