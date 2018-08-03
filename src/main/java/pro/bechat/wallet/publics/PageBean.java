package pro.bechat.wallet.publics;

import java.util.List;

public class PageBean<T> {

    private Integer currentPage=1;                      //当前页数
    private Integer currentSize=10;                     //每页显示条数
    private long totalNum;                              //总条数
    private List<T> items;                              //泛型集合
    /**
     * 总页数
     */
    private int totalPage;

    public PageBean(){}

    public PageBean(Integer currentPage,Integer currentSize,long totaNum){
        super();
        this.currentPage = currentPage;
        this.currentSize = currentSize;
        this.totalNum = totalNum;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Integer currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalPage() {
        if(totalNum%currentSize == 0){
            return (int) (totalNum/currentSize);
        }
        return (int) (totalNum/currentSize)+1;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
