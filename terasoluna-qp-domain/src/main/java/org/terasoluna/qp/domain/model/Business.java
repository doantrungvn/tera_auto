package org.terasoluna.qp.domain.model;

/**
 * @author hunghx
 *
 */
public class Business {
    private Long no;
    private String id;
    private String name;
    private String content;

    public Business(Long no, String id, String name, String content) {
        this.no = no;
        this.id = id;
        this.name =  name;
        this.content = content;
    }

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
