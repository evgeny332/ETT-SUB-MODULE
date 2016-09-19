package com.dndbean;
import java.io.*;

public class CircleWiseReportBean
{

        private String filename;
        //private String dateval;
        private String sdatetime;

        //private String timeval;
       // private String total;
        private String totalcount;


        //private String success ;
        private String successcount ;

        private String failcount;
//      private String updatedate;

        private String edatetime;

        public void setEdatetime(String edatetime){
                this.edatetime = edatetime;
        }
        public String getEdatetime(){

                return edatetime;
        }

//      public void setUpdatetime(String updatetime){
  //              this.updatetime = updatetime;
    //    }
      //  public String getUpdatetime(){
//
 //               return updatetime;
  //      }

       public void setFilename(String filename){
                this.filename = filename;
        }
        public String getFilename(){

                return filename;
        }

        public void setSdatetime(String sdatetime){
                this.sdatetime = sdatetime;
        }
        public String getSdatetime(){

                return sdatetime;
        }

//      public void setTimeval(String timeval){
  //              this.timeval = timeval;
    //    }
      //  public String getTimeval(){

        //        return timeval;
        //}


        public void setTotalcount(String totalcount){
                this.totalcount = totalcount;
        }
        public String getTotalcount(){

                return totalcount;
        }

        public void setSuccesscount(String successcount){
                this.successcount = successcount;
        }
        public String getSuccesscount(){

                return successcount;
        }
     public void setFailcount(String failcount){
                this.failcount = failcount;
        }
        public String getFailcount(){

                return failcount;
        }


}


