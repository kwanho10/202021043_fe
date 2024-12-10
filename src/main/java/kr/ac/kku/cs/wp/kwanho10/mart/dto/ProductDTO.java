package kr.ac.kku.cs.wp.kwanho10.mart.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    
	private Long id;
    private String name;
    private int quantity;
    private String category;
    private Long price;
    private String itemDetail; 
    private String imgUrl;
    
    private MultipartFile imgFile;
    
    
    
    public ProductDTO() {}
    
    public ProductDTO(Long id, String name, int quantity, String category, Long price,String itemDetail, String imgUrl) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.itemDetail=itemDetail;
        this.imgUrl=imgUrl;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
    public Long getPrice() {
		return price;
	}
    public void setPrice(Long price) {
		this.price = price;
	}
    
    
    public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
    
    public String getItemDetail() {
		return itemDetail;
	}
    
    

    
    public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
    
    public String getImgUrl() {
		return imgUrl;
	}
    
    
    public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
    
    public MultipartFile getImgFile() {
		return imgFile;
	}
    
    
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", quantity=" + quantity + ", category=" + category
				+ ", price=" + price + " ]";
	}

    
    
    
    

    
    
}