create table if not exists city (
   id int not null auto_increment comment 'id',
    city_name varchar(40) not null comment '도시이름',
    latitude decimal(10,6) not null comment '위도',
    longitude decimal(10,6) not null comment '경도',
    city_range int not null comment '도시 범위',
   primary key (id)
);

CREATE TABLE IF NOT EXISTS place (
    place_id VARCHAR(255) NOT NULL PRIMARY KEY COMMENT 'Place ID from Google Places API',
    country VARCHAR(100) NOT NULL COMMENT 'Country Name',
    city VARCHAR(100) NOT NULL COMMENT 'City Name',
    place_name VARCHAR(255) NOT NULL COMMENT 'Place Name',
    latitude DECIMAL(10, 6) NOT NULL COMMENT 'Latitude',
    longitude DECIMAL(10, 6) NOT NULL COMMENT 'Longitude',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Timestamp',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Updated Timestamp'
);