package com.demo.winwang.tigermachine.widget;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
    public class UserCaidan {
    @Id(autoincrement = true)
    private Long id;

    private String name;

    private int flag;

    @Generated(hash = 297566103)
    public UserCaidan(Long id, String name, int flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    @Generated(hash = 627857697)
    public UserCaidan() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
