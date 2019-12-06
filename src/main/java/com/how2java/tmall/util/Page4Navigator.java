package com.how2java.tmall.util;

import org.springframework.data.domain.Page;

import java.util.List;

public class Page4Navigator<T> {
    Page<T> pageFromJPA;

    //分页导航栏显示页码数
    int navigatePages;
    //总页数
    int totalPages;
    //当前页码
    int number;
    //总条目数
    long totalElements;
    //页面大小
    int size;
    //当前页面条目数
    int numberOfElements;
    //条目内容
    List<T> content;
    boolean isHasContent;
    boolean first;
    boolean last;
    boolean isHasNext;
    boolean isHasPrevious;
    //分页导航栏页码
    int[] navigatepageNums;

    public Page4Navigator(){

    }

    public Page4Navigator(Page<T> pageFromJPA,int navigatePages){
        this.pageFromJPA=pageFromJPA;
        this.navigatePages=navigatePages;

        totalPages=pageFromJPA.getTotalPages();
        number=pageFromJPA.getNumber();
        totalElements=pageFromJPA.getTotalElements();
        size=pageFromJPA.getSize();
        numberOfElements=pageFromJPA.getNumberOfElements();
        content=pageFromJPA.getContent();
        isHasContent=pageFromJPA.hasContent();
        first=pageFromJPA.isFirst();
        last=pageFromJPA.isLast();
        isHasNext=pageFromJPA.hasNext();
        isHasPrevious=pageFromJPA.hasPrevious();

        calcNavigatepageNums();
    }


    //计算分页导航栏页码数，即初始化navigatepageNums[]数组
    private void calcNavigatepageNums(){
        int navigatepageNums[];
        int totalPages=getTotalPages();
        int num=getNumber();
        if(totalPages<=navigatePages){
            navigatepageNums=new int[totalPages];
            for(int i=0;i<totalPages;i++){
                navigatepageNums[i]=i+1;
            }
        }else {
            navigatepageNums=new int[navigatePages];
            int startNum=num-navigatePages/2;
            int endNum=num+navigatePages/2;
            if(startNum<1){
                startNum=1;
                for(int i=0;i<navigatePages;i++){
                    navigatepageNums[i]=startNum++;
                }
            }else if(endNum>totalPages){
                endNum=totalPages;
                for(int i=navigatePages-1;i>=0;i--){
                    navigatepageNums[i]=endNum--;
                }
            }else {
                for(int i=0;i<navigatePages;i++){
                    navigatepageNums[i]=startNum++;
                }
            }
        }
        this.navigatepageNums=navigatepageNums;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumber() {
        return number;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public List<T> getContent() {
        return content;
    }

    public boolean isHasContent() {
        return isHasContent;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public boolean isHasPrevious() {
        return isHasPrevious;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setHasContent(boolean hasContent) {
        isHasContent = hasContent;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public void setHasNext(boolean hasNext) {
        isHasNext = hasNext;
    }

    public void setHasPrevious(boolean hasPrevious) {
        isHasPrevious = hasPrevious;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
}
