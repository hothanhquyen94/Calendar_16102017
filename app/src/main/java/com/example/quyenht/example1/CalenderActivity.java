package com.example.quyenht.example1;

import java.util.GregorianCalendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;




public class CalenderActivity extends Activity {
    public GregorianCalendar cal_month, cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;
    private TextView tv_year;
    public float toadoX1;
    public float toadoX2;
    private TextView tvNamAmLich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(this, cal_month,CalendarCollection.date_collection_arr);

        tvNamAmLich =  (TextView) findViewById(R.id.tv_xuatThongtin);
        tv_year =  (TextView) findViewById(R.id.tv_year);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText("Tháng "+ android.text.format.DateFormat.format("MM", cal_month));
        tv_year.setText(android.text.format.DateFormat.format("yyyy", cal_month));
        int Year = Integer.parseInt(tv_year.getText().toString());
        String datacurrent = android.text.format.DateFormat.format("yyyy-MM-dd", cal_month).toString();
        String[] NameLunar = VietCalendar.getNameCanChi(datacurrent);
        tvNamAmLich.setText("Ngày "+ NameLunar[0]+", tháng "+NameLunar[1]+",năm "+NameLunar[2]);

        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        /*Lướt màn hình*/
        //Function luot man hinh

        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = CalendarAdapter.day_string
                        .get(position);
                String[] NameLunar = VietCalendar.getNameCanChi(selectedGridDate);
                tvNamAmLich.setText("Ngày "+ NameLunar[0]+", tháng "+NameLunar[1]+",năm "+NameLunar[2]);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
               // String tenNamAmLich = VietCalendar.getLunarYear(Integer.parseInt(separatedTime[0]));
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);

                ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalenderActivity.this);
            }

        });

      //  gridview.setOnTouchListener(new View.OnTouchListener() {
      //      @Override
      //      public boolean onTouch(View view, MotionEvent motionEvent) {
      //          Switch_diplay(motionEvent);
      //          return false;
      //      }
      //  });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Switch_diplay(event);
        return super.onTouchEvent(event);
    }


    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText("Tháng "+android.text.format.DateFormat.format("MM", cal_month));
        tv_year.setText(android.text.format.DateFormat.format("yyyy", cal_month));
        int Year = Integer.parseInt(tv_year.getText().toString());
        String datacurrent = android.text.format.DateFormat.format("yyyy-MM-dd", cal_month).toString();
        String[] NameLunar = VietCalendar.getNameCanChi(datacurrent);
        tvNamAmLich.setText("Ngày "+ NameLunar[0]+", tháng "+NameLunar[1]+",năm "+NameLunar[2]);
    }
    // Function vuot man hinh

    public void Switch_diplay(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                toadoX1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                toadoX2 = event.getX();
                if(toadoX2>toadoX1){
                    setPreviousMonth();
                    refreshCalendar();
                }else{
                    setNextMonth();
                    refreshCalendar();
                }

        }

    }

}