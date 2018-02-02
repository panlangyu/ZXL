package com.travelsky.ypb.domain.model.request;

/**
 * create MsgRequest by huc
 * 2018/1/15  上午11:10
 */
public class MsgRequest {

    private long page;
    private long limit;
    private String appId;
    private String userId;
    private String planId;

    private long start;
    private long end;
    private long [] ids;


    public boolean check(){
        boolean flag = true;
        if (appId == null || appId.equals("")) flag = false;
        if (userId == null || userId.equals("")) flag = false;
        return flag;
    }


    public MsgRequest() {
    }

    public MsgRequest(long page, long limit, String appId, String userId, String planId) {
        this.page = page;
        this.limit = limit;
        this.appId = appId;
        this.userId = userId;
        this.planId = planId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MsgRequest{");
        sb.append("page=").append(page);
        sb.append(", limit=").append(limit);
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", planId='").append(planId).append('\'');
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", ids=");
        if (ids == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < ids.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(ids[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}
