package ahmed.ucas.edu.finalproject;

public class DataViewPager {

   private String naimTitlel;
   private String subTitle;
   private int image;

   public DataViewPager(String naimTitlel, String subTitle, int image) {
      this.naimTitlel = naimTitlel;
      this.subTitle = subTitle;
      this.image = image;
   }


   public String getNaimTitlel() {
      return naimTitlel;
   }

   public String getSubTitle() {
      return subTitle;
   }

   public int getImage() {
      return image;
   }


   public void setNaimTitlel(String naimTitlel) {
      this.naimTitlel = naimTitlel;
   }

   public void setSubTitle(String subTitle) {
      this.subTitle = subTitle;
   }

   public void setImage(int image) {
      this.image = image;
   }
}
