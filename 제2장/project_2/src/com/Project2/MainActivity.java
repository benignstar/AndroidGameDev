package com.Project2;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 전체 화면 모드
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 사용자 View 모드
        setContentView(new MyView(this));
    }
    
    //----------------------------------
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;		// View의 폭과 높이
        int x, y;   		    // 캐릭터의 현재 좌표
        int sx, sy;       		// 캐릭터가 이동할 방향과 거리
    	int rw, rh;             // 캐릭터의 중심점
    	Bitmap rabbit;
    	
    	
        //----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight();   // View의 세로 높이
			
			rabbit = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabbit_1);
			rw = rabbit.getWidth() / 2;		// 캐릭터의 중심점
			rh = rabbit.getHeight() / 2;
			
			x = 100;	// 캐릭터의 초기 좌표
			y = 100;
			sx = 3;		// 캐릭터가 1회에 이동할 거리
			sy = 3;
			
    		mHandler.sendEmptyMessageDelayed(0, 10);  // Handler 호출
		}
		
        //----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			x += sx;				// 수평으로 이동
			y += sy;				// 수직으로 이동

/*          벽을 통과할 경우의 처리			
			if (x < -rw) 		 x = width + rw;
			if (x > width + rw)  x = - rw;
			if (y < -rh) 		 y = height + rh;
			if (y > height + rh) y = - rh;
*/			
			// 벽과 반사할 경우의 처리
			if (x < rw) { 			// 왼쪽 벽과 충동
				x = rw;
				sx =  -sx;
			}
			if (x > width - rw) {	// 오른쪽 벽과 충돌 
				x = width - rw;
				sx =  -sx;
			}
			if (y < rh) { 			// 천정과 충돌
				y = rh;
				sy =  -sy;
			}
			if (y > height - rh) { 	// 바닥과 충돌
				y = height - rh;
				sy =  -sy;
			}
			
			canvas.drawBitmap(rabbit, x - rw, y - rh, null);
		}
		
		 //------------------------------------
        //      Timer Handler
        //------------------------------------
        Handler mHandler = new Handler() {          
        	public void handleMessage(Message msg) {
        		invalidate();		// View를 다시 그림                       
        		mHandler.sendEmptyMessageDelayed(0, 10);
        	}
        }; // Handler
		
    } // MyView
    
} // Activiry

