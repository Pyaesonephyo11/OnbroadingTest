package com.uitest.onbroadingtest

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.uitest.onbroadingtest.ui.theme.OnbroadingTestTheme
import com.uitest.onbroadingtest.ui.theme.Typography
import kotlin.io.path.ExperimentalPathApi

class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor=ContextCompat.getColor(this,R.color.teal_700)
        setContent {
            OnbroadingTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()) {

                    val items =ArrayList<OnBroadingData>()
                    items.add(OnBroadingData(R.drawable.ic_launcher_background,
                        "Shop","We are products in difference"))

                    items.add(OnBroadingData(R.drawable.ic_launcher_foreground,
                        "Shop","We are products in difference"))

                    items.add(OnBroadingData(R.drawable.ic_launcher_background,
                        "Shop","We are products in difference"))


                    val pagerState=rememberPagerState(pageCount = items.size,
                        initialOffscreenLimit = 2,
                        infiniteLoop = false,
                        initialPage = 0)

                    OnBroadingPager(item = items, pagerState = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Black))
                }
            }
        }
    }


    @ExperimentalPagerApi
    @Composable
    fun OnBroadingPager( item : List<OnBroadingData> ,
            pagerState : PagerState, modifier: Modifier=Modifier){

        Box(modifier = modifier){
           Column(horizontalAlignment =Alignment.CenterHorizontally) {
               HorizontalPager(state = pagerState) {
                   page ->
                   Column (modifier = Modifier
                       .padding(top = 60.dp)
                       .fillMaxWidth(),
                       horizontalAlignment = Alignment.CenterHorizontally) {

                       Image(painter = painterResource(id = item[page].image), contentDescription = item[page].title,
                           modifier = Modifier
                               .height(250.dp)
                               .fillMaxWidth())
                       
                       Text(text = item[page].title, modifier = Modifier.padding(top = 10.dp),
                           color = Color.White,
                           style= Typography.bodyLarge
                       )
                       Text(text = item[page].desc, modifier = Modifier.padding(top = 10.dp,
                           start = 20.dp, end = 20.dp),
                           color = Color.White,
                           style= Typography.bodyLarge,
                           fontSize = 18.sp

                       )

                   }
               }

               PagerIndicator(size = item.size, currentPage = pagerState.currentPage)
           }
            Box(modifier = Modifier.align(Alignment.Center)){
                ButtomSection(currentPager = pagerState.currentPage)
            }
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun rememberPagerState(
       @androidx.annotation.IntRange(from = 0) pageCount :Int,
       @androidx.annotation.IntRange(from = 0 ) initialPage : Int =0,
       @FloatRange(from = 0.0 , to=1.0)initialPageOffset : Float =0f,
       @androidx.annotation.IntRange(from=1) initialOffscreenLimit:Int=1,
       infiniteLoop:Boolean =false
    ):PagerState= rememberSaveable(saver = PagerState.Saver){
        PagerState(
            pageCount=pageCount,
            currentPage = initialPage,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffscreenLimit,
            infiniteLoop = infiniteLoop
        )

    }

    @Composable
    fun PagerIndicator( size : Int ,currentPage :Int){
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier =Modifier.padding(top=60.dp)){

            repeat(size){
                Indicator(isSelected = it==currentPage)
            }
        }
    }



    @Composable
    fun Indicator(isSelected:Boolean){
        val with = animateDpAsState(targetValue= if (isSelected) 25.dp else 10.dp)
        
        Box(modifier = Modifier
            .padding(1.dp)
            .height(10.dp)
            .width(with.value)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f)))
            {

            }

    }
    @Composable
    fun ButtomSection(currentPager:Int){
        Row(modifier= Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
            horizontalArrangement = if (currentPager!=2) Arrangement.SpaceBetween else Arrangement.Center){


            if (currentPager ==2){
                OutlinedButton(onClick = { /*TODO*/
                                         },
                    shape = RoundedCornerShape(50)
                )
                {

                    Text(text = "Get Started", modifier = Modifier.padding(vertical = 8.dp,
                        horizontal = 40.dp),
                        color = Color.White)
                }
            }else {


                SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp))
                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp))
            }

        }
    }

    @Composable
    fun SkipNextButton(text:String,modifier:Modifier){
        Text(text = text,color = Color.White,modifier=Modifier,
            fontSize = 18.sp,

            fontWeight = FontWeight.Medium,
            style = Typography.bodyLarge
        )
    }



}