package com.zhuandian.medicineserver.entity;

/**
 * desc :Item实体类
 */
public class ItemEntity {
    private String time;
    private boolean isExtend; //item是否展开
    private SubItem subItem;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setExtend(boolean extend) {
        isExtend = extend;
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public void setSubItem(SubItem subItem) {
        this.subItem = subItem;
    }

    private static class SubItem {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
