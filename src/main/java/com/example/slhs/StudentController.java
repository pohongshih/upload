package com.example.slhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SimplePropertyRowMapper;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class StudentController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查git所有商品
    @GetMapping("/products")
    public ResponseEntity<List> getProducts(){
        List rows = jdbcTemplate.queryForList("SELECT * FROM product");
        return ResponseEntity.ok(rows);
    }

    //查詢某一商品
    @GetMapping("/product")
    public Object getProduct(@RequestParam("pid") int pid){
        try{
            List rows = jdbcTemplate.queryForList("SELECT * FROM product where 編號="+pid);
            return ResponseEntity.ok(rows.get(0));
        }catch (Exception e){
            Map map = new HashMap<>();
            map.put("message","找不到商品!");
            map.put("status",false);
            return map;
        }
    }

    //新增
    @PostMapping("/product/add")
    public ResponseEntity<List> addProduct(@RequestBody Product p){
        String sql = "INSERT INTO product(品名,價格,圖片,說明) values('" + p.getName() + "','" + p.getPrice() + "','" + p.getPath() + "','" + p.getDescription() + "')";
        jdbcTemplate.update(sql);
        List rows = jdbcTemplate.queryForList("SELECT * FROM product");
        return ResponseEntity.ok(rows);
    }

    //更新
    @PostMapping("/product/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product p,@RequestParam("pid") int pid){
        String sql = "UPDATE product SET 品名='" + p.getName() + "',價格='" + p.getPrice() + "',圖片='" + p.getPath() + "',說明='" + p.getDescription() + "' WHERE 編號="+pid;
        jdbcTemplate.update(sql);
        List rows = jdbcTemplate.queryForList("SELECT * FROM product");
        return ResponseEntity.ok(rows);
    }

    //刪除
    @PostMapping("/product/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam("pid") int pid){
        String sql = "DELETE FROM product WHERE 編號=" + pid;
        jdbcTemplate.update(sql);
        List rows = jdbcTemplate.queryForList("SELECT * FROM product");
        return ResponseEntity.ok(rows);
    }

}
