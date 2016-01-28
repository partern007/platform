package open.platform.web.controller.utils;

import java.util.Date;

public class FormsAuthenticationTicket {
	
 	private final static ThreadLocal<FormsAuthenticationTicket> holder = 
 			new ThreadLocal<FormsAuthenticationTicket>();
    private int _version = 0;
    private String _userName ;
    private String _userData ;
    private String _appPath ;
    private Date _expires ;
    private Date _issueDate;
    private boolean _isPersistent = false;

    public int getVersion()
    {
       return _version;
    }
    public String getUsername()
    {
        return _userName;
    }
    public String getUserData()
    {
        return _userData;
    }
    public String getAppPath()
    {
        return _appPath;
    }
    public Date getExpires()
    {
        return _expires;
    }
    public Date getIssueDate()
    {
        return _issueDate;
    }
    public boolean isPersistent()
    {
        return _isPersistent;
    }

    /**
     * 判断cookie是不是过期
     * @return true 过期。false 未过期
     */
    public boolean isExpired()
    {
    	return System.currentTimeMillis() > _expires.getTime();
    }

    public FormsAuthenticationTicket(String userName, String userdata, String appPath,
                                     Date issued, Date expires, int version, boolean isPersistent) throws Exception {
        if (userName == null ||"".equals(userName))
            throw new Exception("userName is null");
        else
            _userName = userName;
        if (userdata == null)
            _userData = "";
        else
            _userData = userdata;
        if (appPath == null)
            _appPath = "/";
        else if (!appPath.startsWith("/"))
            _appPath = "/" + appPath;
        else
            _appPath = appPath;
        if (issued == null)
            _issueDate = new Date();
        else
            _issueDate = issued;
        if (expires == null)
            _expires = new Date(System.currentTimeMillis() + 30* 1000 * 60 );     //30 mins
        else
            _expires = expires;
        if (version > 0)
            _version = version;
        else
            _version = 1;
        
        _isPersistent = isPersistent;

    }

    public String toString() {
         return "version="+_version+"," +
                 "userName="+_userName+"," +
                 "userData="+_userData+"," +
                 "appPath="+_appPath+"," +
                 "isPersistent="+_isPersistent+"," +
                 "issueDate="+_issueDate+"," +
                 "expires="+_expires ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormsAuthenticationTicket ticket = (FormsAuthenticationTicket) o;

        if (_isPersistent != ticket._isPersistent) return false;
        if (_version != ticket._version) return false;
        if (_appPath != null ? !_appPath.equals(ticket._appPath) : ticket._appPath != null) return false;
        if (_expires != null ? !_expires.equals(ticket._expires) : ticket._expires != null) return false;
        if (_issueDate != null ? !_issueDate.equals(ticket._issueDate) : ticket._issueDate != null) return false;
        if (_userData != null ? !_userData.equals(ticket._userData) : ticket._userData != null) return false;
        if (_userName != null ? !_userName.equals(ticket._userName) : ticket._userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _version;
        result = 31 * result + (_userName != null ? _userName.hashCode() : 0);
        result = 31 * result + (_userData != null ? _userData.hashCode() : 0);
        result = 31 * result + (_appPath != null ? _appPath.hashCode() : 0);
        result = 31 * result + (_expires != null ? _expires.hashCode() : 0);
        result = 31 * result + (_issueDate != null ? _issueDate.hashCode() : 0);
        result = 31 * result + (_isPersistent ? 1 : 0);
        return result;
    }

    public void set_issueDate(Date _issueDate) {
        this._issueDate = _issueDate;
    }
    
    /**
     * 实际上是将FormsAuthenticationTicket放到了actionContext中
     * 
     * @param ticket
     */
    public static void setTicket(FormsAuthenticationTicket ticket) {
        holder.set(ticket);
    }

    /**
     * 取出ticket的上下文
     * 
     * @return null 如果没有的话
     */
    public static FormsAuthenticationTicket getTicket() {
        return holder.get();
    }

    /**
     * 删除上下文
     */
    public static void remove() {
        holder.remove();
    }

}