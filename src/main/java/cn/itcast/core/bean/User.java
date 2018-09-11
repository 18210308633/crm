package cn.itcast.core.bean;

public class User {
    private Integer id;  //主键

    private String user_name;  //用户名

    private String password;  //密码

    private Integer age;  //年龄
    
    private String deptid;  //部门id
    
    private String deptidname;  //部门名称
    
    private String createdate;  //创建时间
    
    private String persontype;  //人员类型
    
    private String name;  //用户姓名
    
    private String tellphone;  //联系方式
    
    private String email;  //电子邮件
    
    private String isdisable;  //是否禁用
    
    private String updatedate;  //修改时间
    
    private Integer start;
    
	private Integer rows;
    public User() {
	}

	public User(String user_name, String password) {
		super();
		this.user_name = user_name;
		this.password = password;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptidname() {
		return deptidname;
	}

	public void setDeptidname(String deptidname) {
		this.deptidname = deptidname;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getPersontype() {
		return persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTellphone() {
		return tellphone;
	}

	public void setTellphone(String tellphone) {
		this.tellphone = tellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(String isdisable) {
		this.isdisable = isdisable;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
    
    
}