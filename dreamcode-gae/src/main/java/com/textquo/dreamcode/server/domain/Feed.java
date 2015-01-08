package com.textquo.dreamcode.server.domain;

import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Id;

@Entity
public class Feed {
    @Id
    private Long id;
    private String ownerId;
}
