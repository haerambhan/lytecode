package CPPService;

import CPPService.CPP14Parser.IterationStatementContext;
import Model.IterationData;

public class MyCPPListener extends CPP14ParserBaseListener {
	
	private int loopLevel = 0;
	private IterationData data;
	public MyCPPListener() {
		data = new IterationData();
	}
	@Override
	public void enterIterationStatement(IterationStatementContext ctx) {
		// TODO Auto-generated method stub
		super.enterIterationStatement(ctx);
		this.data.loopCount++;
		this.loopLevel++;
		if(loopLevel >= this.data.maxLevel ) this.data.maxLevel = loopLevel;
		
	}

	@Override
	public void exitIterationStatement(CPP14Parser.IterationStatementContext ctx) {
		super.exitIterationStatement(ctx);
		this.loopLevel --;
	}

	public IterationData getData() {
		return data;
	}

	public void setData(IterationData data) {
		this.data = data;
	}
}
