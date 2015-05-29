package com.reem.smartbudget.smartbudgetui;

import com.reem.smartbudget.BudgetPreferences;
import com.reem.smartbudget.R;
import com.reem.smartbudget.R.layout;
import com.reem.smartbudget.smartbudgetcontent.ProviderBudget;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentReports extends Fragment {
	private GraphicalView graphView;
	private GraphicalView graphView2;

	public String getCurrency() {
		String symbol = BudgetPreferences.loadCurrencyFromFile(getActivity().getApplicationContext());
		if(symbol.equals("USD"))
			return "$";
		else if(symbol.equals("EURO"))
			return "€";
		else if(symbol.equals("POUND"))
			return "£";
    else if(symbol.equals("AUD"))
      return "AU$";
		else
			return "¥";	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_reports, container,
				false);
		String totalIncome = ProviderBudget.getTotalIncome(getActivity());
		String totalExpense = ProviderBudget.getTotalExpense(getActivity())
				.replace("-", "");

		String remainingIncome = ""
				+ (Integer.parseInt(totalIncome) - Integer
						.parseInt(totalExpense));
		String spendingIncome = totalExpense;

		String remainingExpense = "" + (Integer.parseInt(totalIncome) - Integer
				.parseInt(totalExpense));
		String spendingExpense = totalExpense;


		LinearLayout chartContainerlayout = (LinearLayout) rootView.findViewById(R.id.graphHolder);
		LinearLayout chartContainerlayout2 = (LinearLayout) rootView.findViewById(R.id.graphHolder2);
		TextView income = (TextView) rootView.findViewById(R.id.totalIncome);
		TextView expense = (TextView) rootView.findViewById(R.id.totalExpense);
		
		String symbol = getCurrency();
		income.setText("Total Income: " + symbol + totalIncome.toString());
		expense.setText("Total Expense: " + symbol + totalExpense.toString());
		
         CategorySeries series = new CategorySeries("pie"); // adding series of charts from array .
             series.add("Savings", Double.parseDouble(remainingIncome));          
             series.add("Spendings", Double.parseDouble(spendingIncome));
             int []colors = new int[]{Color.argb(255, 255, 165, 0), Color.BLUE};       
                      DefaultRenderer renderer = new DefaultRenderer();
                      for(int color : colors){
                          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
                          r.setColor(color);
//                                r.setDisplayBoundingPoints(true);
//                                r.setDisplayChartValuesDistance(5);
                          r.setDisplayChartValues(true);
                          r.setChartValuesTextSize(15);
                          renderer.addSeriesRenderer(r);
                      }
                      renderer.isInScroll();
                      renderer.setZoomButtonsVisible(false);   //setting zoom button of Graph
                      renderer.setApplyBackgroundColor(false);
                      renderer.setBackgroundColor(Color.TRANSPARENT); //setting background color of chart
                      renderer.setChartTitle("INCOME");   //setting title of chart
                      renderer.setChartTitleTextSize((float) 30);
                      renderer.setShowLabels(true);
                      renderer.setLabelsTextSize(20);
                      renderer.setLegendTextSize(25);
                      renderer.setPanEnabled(false); 
                      renderer.setDisplayValues(true);
                      graphView = ChartFactory.getPieChartView(getActivity(),
                             series, renderer);

                      // Adding the pie chart to the custom layout
                      chartContainerlayout.addView(graphView);
                      
           
                     CategorySeries series2 = new CategorySeries("pie"); 
                         series2.add("Remaining",Double.parseDouble(remainingExpense));          
                         series2.add("Spending", Double.parseDouble(spendingExpense));
                         int []colors2 = new int[]{Color.argb(255, 255, 165, 0), Color.BLUE};            
                                  DefaultRenderer renderer2 = new DefaultRenderer();
                                  for(int color : colors2){
                                      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
                                      r.setColor(color);
//                                            r.setDisplayBoundingPoints(true);
//                                            r.setDisplayChartValuesDistance(5);
                                      r.setDisplayChartValues(true);
                                      r.setChartValuesTextSize(15);
                                      renderer2.addSeriesRenderer(r);
                                  }
                                  renderer2.isInScroll();
                                  renderer2.setZoomButtonsVisible(false);   
                                  renderer2.setApplyBackgroundColor(false);
                                  renderer2.setBackgroundColor(Color.TRANSPARENT); 
                                  renderer2.setChartTitle("EXPENSES");   
                                  renderer2.setChartTitleTextSize((float) 30);
                                  renderer2.setShowLabels(true);
                                  renderer2.setDisplayValues(true);
                                  renderer2.setLabelsTextSize(20);
                                  renderer2.setPanEnabled(false); 
                                  renderer2.setLegendTextSize(25);
                                  graphView2 = ChartFactory.getPieChartView(getActivity(),
                                         series2, renderer2);

                                  // Adding the pie chart to the custom layout
                                  chartContainerlayout2.addView(graphView2);
		return rootView;
	}

}
