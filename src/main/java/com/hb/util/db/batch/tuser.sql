DROP TABLE IF EXISTS tuser; 

CREATE TABLE tuser ( 
    id bigint(20) NOT NULL AUTO_INCREMENT, 
    name varchar(12) DEFAULT NULL, 
    remark varchar(24) DEFAULT NULL, 
    createtime datetime DEFAULT NULL, 
    updatetime datetime DEFAULT NULL, 
    PRIMARY KEY (id) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;