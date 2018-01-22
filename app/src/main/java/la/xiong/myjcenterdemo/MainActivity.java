package la.xiong.myjcenterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import la.xiong.seekbar.RangeSeekBar;

public class MainActivity extends AppCompatActivity {

    private RangeSeekBar mRangeSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRangeSeekBar = (RangeSeekBar)findViewById(R.id.seekBar);
        mRangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("RangeSeekBar's progressing value is " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("RangeSeekBar's start value is " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //滑动或者点击停止时的回调
                System.out.println("RangeSeekBar's stop value is " + seekBar.getProgress());
                Toast.makeText(MainActivity.this, "当前位置是"+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
