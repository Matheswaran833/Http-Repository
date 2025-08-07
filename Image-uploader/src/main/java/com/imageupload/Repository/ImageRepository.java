package com.imageupload.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imageupload.Entity.Image;

public interface ImageRepository extends JpaRepository <Image,Long>{

}
