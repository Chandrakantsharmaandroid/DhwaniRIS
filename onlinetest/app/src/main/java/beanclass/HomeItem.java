package beanclass;

/**
 * @author manish.s
 */

public class HomeItem {
    int image;
    String title;
    String itemId;
    int badgecount;

    public HomeItem(int image, String title, String itemId, int badgecount) {
        super();
        this.image = image;
        this.title = title;
        this.itemId = itemId;
        this.badgecount = badgecount;
    }

    public int getBadgecount() {
        return badgecount;
    }

    public void setBadgecount(int badgecount) {
        this.badgecount = badgecount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


}
