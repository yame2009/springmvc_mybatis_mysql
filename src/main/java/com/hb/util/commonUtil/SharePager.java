package com.hb.util.commonUtil;
/**
 * 分页工具类
 *
 */
public class SharePager {
    private int totalRows; //总行数
    private int pageSize = 20; //每页显示的行数  
    private int currentPage; //当前页号
    private int totalPages; //总页数 
    private int startRow; //当前页在数据库中的起始行  
    
 
    /**
     * 默认构造函数
     */
    public SharePager() 
    {  
         
    }  
       
    /**默认每页10行
     * @param totalRows 
     */ 
    public SharePager(int totalRows) 
    {  
        this.totalRows = totalRows;  
 
        totalPages =(int) Math.ceil((double)totalRows / (double)pageSize);  
        startRow = 0;  
    }  
       
       
    /**可自定义每页显示多少页
     * @param totalRows 
     * @param pageSize 
     */ 
    public SharePager(int totalRows, int pageSize) 
    {  
        this.totalRows = totalRows;  
        this.pageSize = pageSize; 
        if(this.pageSize<1)               
            this.pageSize=1;
        else if(pageSize>20)
            this.pageSize=20;
 
//        if(this.pageSize>totalRows){
//          this.pageSize=(int)totalRows;
//        }
         
     
 
        totalPages =(int) Math.ceil((double)totalRows / (double)this.pageSize);   
        currentPage = 1;  
        startRow = 0;  
    }  
       
    /**
     * 跳转到首页
     */
    public void first() 
    {  
        this.currentPage = 1;  
        this.startRow = 0; 
    }  
     
    /**
     * 跳转到上一页
     */
    public void previous() 
    {  
        if (currentPage == 1) 
        {  
            return;  
        }  
        currentPage--;  
        startRow = (currentPage-1) * pageSize;  
   }  
     
    /**
     * 跳转到下一页
     */
    public void next() 
    {  
        if (currentPage < totalPages)
        {  
            currentPage++;  
        }  
        startRow = (currentPage-1) * pageSize;  
    }  
     
    /**
     *  跳转到尾页
     */
    public void last() 
    {  
        this.currentPage = totalPages;  
        if(currentPage<1){
            currentPage = 1;
        }
        this.startRow = (currentPage-1) * this.pageSize;
        totalPages =(int) Math.ceil((double)totalRows / (double)this.pageSize);   
         
    }  
     
    /**
     * 跳转到指定页
     * @param currentPage   指定的页
     */
    public void refresh(int currentPage) 
    {  
         
        if(currentPage < 0)
        {
            first();
        }
        if (currentPage > totalPages) 
        {  
            last();  
        }else{
            this.currentPage = currentPage;
            this.startRow = (currentPage-1) * this.pageSize;
          }
  
    }  
      
       
    public int getStartRow()
    {  
        return startRow;  
    }  
     
    public int getTotalPages()
    {  
        return totalPages;  
    }  
     
    public int getCurrentPage()
    {  
        return currentPage;  
    }  
     
    public int getPageSize() 
    {  
        return pageSize;  
    }  
     
    public void setTotalRows(int totalRows) 
    {  
        this.totalRows = totalRows;  
    }  
     
    public void setStartRow(int startRow)
    {  
        this.startRow = startRow;  
    }  
     
    public void setTotalPages(int totalPages) 
    {  
        this.totalPages = totalPages;  
    }  
     
    public void setCurrentPage(int currentPage) 
    {  
        this.currentPage = currentPage;  
    } 
     
    public void setPageSize(int pageSize) 
    {  
        this.pageSize = pageSize;  
    }  
     
    public int getTotalRows() 
    {  
        return totalRows;  
    }  
}
