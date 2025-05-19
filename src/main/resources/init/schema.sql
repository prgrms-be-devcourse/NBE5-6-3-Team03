create table if not exists country (
   id int not null auto_increment comment 'id',
   country_name varchar(40) not null comment '국가이름',
    city_name varchar(40) not null comment '도시이름',
    latitude decimal(10,6) not null comment '위도',
    longitude decimal(10,6) not null comment '경도',
    city_range int not null comment '도시 범위',
   primary key (id)
);

CREATE TABLE IF NOT EXISTS place (
    place_id VARCHAR(255) NOT NULL PRIMARY KEY COMMENT 'Place ID from Google Places API',
    country VARCHAR(100) NOT NULL COMMENT '국가이름',
    city VARCHAR(100) NOT NULL COMMENT '도시이름',
    place_name VARCHAR(255) NOT NULL COMMENT '장소명',
    latitude DECIMAL(10, 6) NOT NULL COMMENT '위도',
    longitude DECIMAL(10, 6) NOT NULL COMMENT '경도',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);