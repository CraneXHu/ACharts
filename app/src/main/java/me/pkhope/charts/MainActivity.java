package me.pkhope.charts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.google.gson.Gson;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/index.html");

        webView.setWebViewClient(new MyWebViewClient());

        Button lineButton = (Button) findViewById(R.id.line);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLineChart();
            }
        });

        Button barButton = (Button) findViewById(R.id.bar);
        barButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarChart();
            }
        });

        Button pieButton = (Button) findViewById(R.id.pie);
        pieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPieChart();
            }
        });
    }

    private void showLineChart(){

        webView.loadUrl("javascript:clear()");

        Option option = new Option();
        option.legend("高度(km)与气温(°C)变化关系");

//            option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("{value} km");
        categoryAxis.boundaryGap(false);
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
        option.yAxis(categoryAxis);

        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);

        Gson gson = new Gson();
        String jsonOption = gson.toJson(option);

        webView.loadUrl("javascript:setOption("+jsonOption+")");
    }

    private void showBarChart(){

        webView.loadUrl("javascript:clear()");

        Option option = new Option();
        option.legend("销量");

//            option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

        option.tooltip().show(true);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.data("衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子");
        option.xAxis(categoryAxis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        Bar bar = new Bar();
        bar.name("销量").data(5, 20, 40, 10, 10, 20);
        option.series(bar);

        Gson gson = new Gson();
        String jsonOption = gson.toJson(option);

        webView.loadUrl("javascript:setOption("+jsonOption+")");

    }

    private void showPieChart(){

        webView.loadUrl("javascript:clear()");

        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label(new Label().show(false)).labelLine().show(false);

        ItemStyle placeHolderStyle = new ItemStyle();
        placeHolderStyle.normal().color("rgba(0,0,0,0)").label(new Label().show(false)).labelLine().show(false);
        placeHolderStyle.emphasis().color("rgba(0,0,0,0)");

        Option option = new Option();
        option.title().text("你幸福吗？")
                .subtext("From ExcelHome")
                .x(X.center)
                .y(Y.center)
                .itemGap(20)
                .textStyle().color("rgba(30,144,255,0.8)")
                .fontFamily("微软雅黑")
                .fontSize(35)
                .fontWeight("bolder");
        option.tooltip().show(true).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical)
                .x("(function(){return document.getElementById('main').offsetWidth / 2;})()")
                .y(56)
                .itemGap(12)
                .data("68%的人表示过的不错", "29%的人表示生活压力很大", "3%的人表示“我姓曾”");
//        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);

        Pie p1 = new Pie("1");
        p1.clockWise(false).radius(125, 150).itemStyle(dataStyle)
                .data(new com.github.abel533.echarts.data.Data("68%的人表示过的不错", 68), new com.github.abel533.echarts.data.Data("invisible", 32).itemStyle(placeHolderStyle));

        Pie p2 = new Pie("2");
        p2.clockWise(false).radius(100, 125).itemStyle(dataStyle)
                .data(new com.github.abel533.echarts.data.Data("29%的人表示生活压力很大", 29), new com.github.abel533.echarts.data.Data("invisible", 71).itemStyle(placeHolderStyle));

        Pie p3 = new Pie("3");
        p3.clockWise(false).radius(75, 100).itemStyle(dataStyle)
                .data(new com.github.abel533.echarts.data.Data("3%的人表示“我姓曾”", 3), new com.github.abel533.echarts.data.Data("invisible", 97).itemStyle(placeHolderStyle));

        option.series(p1, p2, p3);

        Gson gson = new Gson();
        String jsonOption = gson.toJson(option);

        webView.loadUrl("javascript:setOption("+jsonOption+")");
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            showLineChart();
        }
    }

}
