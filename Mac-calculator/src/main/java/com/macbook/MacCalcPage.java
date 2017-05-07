package com.macbook;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

public class MacCalcPage extends WebPage {
	private BigDecimal firstArgument;
	private BigDecimal secondArgument;
	private String input = "0";
	private boolean refreshinput = false;
	private boolean refreshArg = false;
	private String oper;

	public MacCalcPage() {
		add(new CalcForm("form"));
	}

	class CalcForm extends Form {
		public CalcForm(String id) {
			super(id);
			add(new TextField("input", new PropertyModel(MacCalcPage.this, "input")));
			// Not good idea to place in one row, but it is for the place save
			add(new Button("input1") {public void onSubmit() {enterSymbol("1");}});
			add(new Button("input2") {public void onSubmit() {enterSymbol("2");}});
			add(new Button("input3") {public void onSubmit() {enterSymbol("3");}});
			add(new Button("input4") {public void onSubmit() {enterSymbol("4");}});
			add(new Button("input5") {public void onSubmit() {enterSymbol("5");}});
			add(new Button("input6") {public void onSubmit() {enterSymbol("6");}});
			add(new Button("input7") {public void onSubmit() {enterSymbol("7");}});
			add(new Button("input8") {public void onSubmit() {enterSymbol("8");}});
			add(new Button("input9") {public void onSubmit() {enterSymbol("9");}});
			add(new Button("input0") {public void onSubmit() {enterSymbol("0");}});
			add(new Button("inputp") {public void onSubmit() {enterSymbol(".");}});
			add(new Button("inputs") {public void onSubmit() {enterSymbol("-");}});

			add(new Button("add") {public void onSubmit() {setOper("add");}});
			add(new Button("mult") {public void onSubmit() {setOper("mult");}});
			add(new Button("dev") {public void onSubmit() {setOper("dev");}});
			add(new Button("sub") {public void onSubmit() {setOper("sub");}});

			//TODO
			add(new Button("perc") {
				public void onSubmit() {
					calculate(true);
				}
			});
			add(new Button("eq") {
				public void onSubmit() {
					calculate(false);
				}
			});
			add(new Button("clear") {
				public void onSubmit() {
					input = "0";
					oper = null;
				}
			});
		}
	}

	private void setOper(String oper) {
		refreshinput = true;
		if (input.isEmpty()) return;

		if (refreshArg) {
			try {
				firstArgument = new BigDecimal(input);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				input = "Wrong Inp";
				return ;
			} 
		} 
		this.oper = oper;
	}
	
	private void enterSymbol(String symbol) {
		if (input.length() == 10) return;
		
		if (input.equals("0") || refreshinput) {
			if (symbol.equals("-")) {
				return;
			} else if (symbol.equals(".")) {
				input = "0.";
			} else {
				input = symbol;
			}
			refreshinput = false;
		} else {
			if (symbol.equals(".")) {
				if (input.contains("."))
					return;
			}
			StringBuilder str = new StringBuilder(input);
			if (symbol.equals("-")) {
				if (input.equals("0.")) {
					return;
				}
				if (str.charAt(0) == '-') {
					str.deleteCharAt(0);
				} else {
					str.insert(0, symbol);
				}
			} else {
				str.append(symbol);
			}
			input = str.toString();
		}
		refreshArg = true;
	}

	void calculate(boolean inPercents) {
		if (oper == null) return;

		refreshinput = true;
		
		if (refreshArg) {
			try {
				secondArgument = new BigDecimal(input);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				input = "Wrong Inp";
				return;
			}
			refreshArg = false;
		}
//		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//		dfs.setDecimalSeparator(',');		
//		DecimalFormat df = new DecimalFormat("0.##", dfs);
		DecimalFormat df = new DecimalFormat("0.##");
		if (inPercents) {
			secondArgument = firstArgument.multiply(secondArgument.divide(BigDecimal.valueOf(100)));
			input = df.format(secondArgument);
			return;
		}
		if (oper.equals("add")) {
			firstArgument = firstArgument.add(secondArgument);
		} else if (oper.equals("sub")) {
			firstArgument = firstArgument.subtract(secondArgument);
		} else if (oper.equals("mult")) {
			firstArgument = firstArgument.multiply(secondArgument).stripTrailingZeros();
		} else if (oper.equals("dev")) {
			if (secondArgument == BigDecimal.ZERO) {
				input = "Udefined";
			}else {
				firstArgument = firstArgument.divide(secondArgument, 4, RoundingMode.HALF_UP);
			}
		}
		input = df.format(firstArgument);
	}
}
