package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.Blog;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> list1 = Arrays.asList(new Blog(1,"In the past, when we wanted to store more data or increase our processing power, the common option was to scale vertically (get more powerful machines) or further optimize the existing code base. However, with the advances in parallel processing and distributed systems, it is more common to expand horizontally, or have more machines to do the same task in parallel. We can already see a bunch of data manipulation tools in the Apache project like Spark, Hadoop, Kafka, Zookeeper and Storm. However, in order to effectively pick the tool of choice, a basic idea of CAP Theorem is necessary. CAP Theorem is a concept that a distributed database system can only have 2 of the 3: Consistency, Availability and Partition Tolerance.\n" +
                "\n" +
                "CAP Theorem is very important in the Big Data world, especially when we need to make trade off’s between the three, based on our unique use case. On this blog, I will try to explain each of these concepts and the reasons for the trade off. I will avoid using specific examples as DBMS are rapidly evolving.","https://www.cloudflare.com/img/learning/dns/what-is-dns/dns-record-request-sequence-2.png", Blog.Tag.SYSTEM_DESIGN_FUNDAMENTAL,"CAP Theorem"),
                new Blog(2,"In the past, when we wanted to store more data or increase our processing power, the common option was to scale vertically (get more powerful machines) or further optimize the existing code base. However, with the advances in parallel processing and distributed systems, it is more common to expand horizontally, or have more machines to do the same task in parallel. We can already see a bunch of data manipulation tools in the Apache project like Spark, Hadoop, Kafka, Zookeeper and Storm. However, in order to effectively pick the tool of choice, a basic idea of CAP Theorem is necessary. CAP Theorem is a concept that a distributed database system can only have 2 of the 3: Consistency, Availability and Partition Tolerance.\n" +
                        "\n" +
                        "CAP Theorem is very important in the Big Data world, especially when we need to make trade off’s between the three, based on our unique use case. On this blog, I will try to explain each of these concepts and the reasons for the trade off. I will avoid using specific examples as DBMS are rapidly evolving.","https://www.cloudflare.com/img/learning/dns/what-is-dns/dns-record-request-sequence-2.png", Blog.Tag.SYSTEM_DESIGN_FUNDAMENTAL,"CAP Theorem")
                );
        return list1;
    }

    @Override
    public List<Blog> getAllBlogsByTags(com.s2s.scaletoscale.models.request.Blog.Tag tag, Pageable pageable) {
        return null;
    }
}
