-- 상위 카테고리 (1뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (0, 'MENS', '남성 의류', 1, NOW(), NOW()),
                                                                                  (0, 'WOMENS', '여성 의류', 1, NOW(), NOW()),
                                                                                  (0, 'KIDS', '아동 의류', 1, NOW(), NOW());

-- 남성 의류 하위 카테고리 (2뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (1, 'M_TOPS', '남성 상의', 2, NOW(), NOW()),
                                                                                  (1, 'M_BOTTOMS', '남성 하의', 2, NOW(), NOW()),
                                                                                  (1, 'M_OUTERWEAR', '남성 아우터', 2, NOW(), NOW());

-- 여성 의류 하위 카테고리 (2뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (2, 'W_TOPS', '여성 상의', 2, NOW(), NOW()),
                                                                                  (2, 'W_BOTTOMS', '여성 하의', 2, NOW(), NOW()),
                                                                                  (2, 'W_OUTERWEAR', '여성 아우터', 2, NOW(), NOW());

-- 아동 의류 하위 카테고리 (2뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (3, 'K_TOPS', '아동 상의', 2, NOW(), NOW()),
                                                                                  (3, 'K_BOTTOMS', '아동 하의', 2, NOW(), NOW()),
                                                                                  (3, 'K_OUTERWEAR', '아동 아우터', 2, NOW(), NOW());

-- 남성 상의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (4, 'M_T_SHIRTS', '남성 티셔츠', 3, NOW(), NOW()),
                                                                                  (4, 'M_SHIRTS', '남성 셔츠', 3, NOW(), NOW()),
                                                                                  (4, 'M_HOODIES', '남성 후드티', 3, NOW(), NOW());

-- 남성 하의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (5, 'M_JEANS', '남성 청바지', 3, NOW(), NOW()),
                                                                                  (5, 'M_SLACKS', '남성 슬랙스', 3, NOW(), NOW()),
                                                                                  (5, 'M_SHORTS', '남성 반바지', 3, NOW(), NOW());

-- 남성 아우터 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (6, 'M_JACKETS', '남성 자켓', 3, NOW(), NOW()),
                                                                                  (6, 'M_COATS', '남성 코트', 3, NOW(), NOW()),
                                                                                  (6, 'M_BLAZERS', '남성 블레이저', 3, NOW(), NOW());

-- 여성 상의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (7, 'W_T_SHIRTS', '여성 티셔츠', 3, NOW(), NOW()),
                                                                                  (7, 'W_SHIRTS', '여성 셔츠', 3, NOW(), NOW()),
                                                                                  (7, 'W_HOODIES', '여성 후드티', 3, NOW(), NOW());

-- 여성 하의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (8, 'W_JEANS', '여성 청바지', 3, NOW(), NOW()),
                                                                                  (8, 'W_SKIRTS', '여성 치마', 3, NOW(), NOW()),
                                                                                  (8, 'W_SHORTS', '여성 반바지', 3, NOW(), NOW());

-- 여성 아우터 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (9, 'W_JACKETS', '여성 자켓', 3, NOW(), NOW()),
                                                                                  (9, 'W_COATS', '여성 코트', 3, NOW(), NOW()),
                                                                                  (9, 'W_BLAZERS', '여성 블레이저', 3, NOW(), NOW());

-- 아동 상의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (10, 'K_T_SHIRTS', '아동 티셔츠', 3, NOW(), NOW()),
                                                                                  (10, 'K_SHIRTS', '아동 셔츠', 3, NOW(), NOW()),
                                                                                  (10, 'K_HOODIES', '아동 후드티', 3, NOW(), NOW());

-- 아동 하의 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (11, 'K_JEANS', '아동 청바지', 3, NOW(), NOW()),
                                                                                  (11, 'K_SHORTS', '아동 반바지', 3, NOW(), NOW()),
                                                                                  (11, 'K_PANTS', '아동 바지', 3, NOW(), NOW());

-- 아동 아우터 하위 카테고리 (3뎁스)
INSERT INTO Categories (parent_id, code, name, depth, created_At, updated_At) VALUES
                                                                                  (12, 'K_JACKETS', '아동 자켓', 3, NOW(), NOW()),
                                                                                  (12, 'K_COATS', '아동 코트', 3, NOW(), NOW()),
                                                                                  (12, 'K_BLAZERS', '아동 블레이저', 3, NOW(), NOW());
-- 제품 정보를 저장하는 INSERT문
INSERT INTO Products (category_id, prod_name, prod_desc, stock, price, thumbnail_url, created_At, updated_At) VALUES
                                                                                                                  (4, '남성 티셔츠 1', '편안한 남성 티셔츠입니다.', 100, 19900.00, 'thumbnail_url_1.jpg', NOW(), NOW()),
                                                                                                                  (4, '남성 티셔츠 2', '스타일리시한 남성 티셔츠입니다.', 150, 29900.00, 'thumbnail_url_2.jpg', NOW(), NOW()),
                                                                                                                  (5, '남성 셔츠 1', '고급스러운 남성 셔츠입니다.', 80, 49900.00, 'thumbnail_url_3.jpg', NOW(), NOW()),
                                                                                                                  (6, '남성 후드티 1', '따뜻한 남성 후드티입니다.', 120, 39900.00, 'thumbnail_url_4.jpg', NOW(), NOW()),
                                                                                                                  (7, '여성 티셔츠 1', '편안한 여성 티셔츠입니다.', 200, 19900.00, 'thumbnail_url_5.jpg', NOW(), NOW()),
                                                                                                                  (8, '여성 셔츠 1', '고급스러운 여성 셔츠입니다.', 90, 49900.00, 'thumbnail_url_6.jpg', NOW(), NOW()),
                                                                                                                  (9, '여성 후드티 1', '따뜻한 여성 후드티입니다.', 130, 39900.00, 'thumbnail_url_7.jpg', NOW(), NOW()),
                                                                                                                  (10, '아동 티셔츠 1', '귀여운 아동 티셔츠입니다.', 150, 14900.00, 'thumbnail_url_8.jpg', NOW(), NOW()),
                                                                                                                  (11, '아동 셔츠 1', '멋진 아동 셔츠입니다.', 70, 29900.00, 'thumbnail_url_9.jpg', NOW(), NOW()),
                                                                                                                  (12, '아동 후드티 1', '따뜻한 아동 후드티입니다.', 110, 19900.00, 'thumbnail_url_10.jpg', NOW(), NOW()),
                                                                                                                  (5, '남성 셔츠 2', '세련된 남성 셔츠입니다.', 85, 45900.00, 'thumbnail_url_11.jpg', NOW(), NOW()),
                                                                                                                  (8, '여성 셔츠 2', '세련된 여성 셔츠입니다.', 95, 45900.00, 'thumbnail_url_12.jpg', NOW(), NOW()),
                                                                                                                  (11, '아동 셔츠 2', '세련된 아동 셔츠입니다.', 75, 25900.00, 'thumbnail_url_13.jpg', NOW(), NOW()),
                                                                                                                  (4, '남성 티셔츠 3', '심플한 남성 티셔츠입니다.', 110, 17900.00, 'thumbnail_url_14.jpg', NOW(), NOW()),
                                                                                                                  (7, '여성 티셔츠 2', '심플한 여성 티셔츠입니다.', 210, 17900.00, 'thumbnail_url_15.jpg', NOW(), NOW()),
                                                                                                                  (10, '아동 티셔츠 2', '심플한 아동 티셔츠입니다.', 160, 12900.00, 'thumbnail_url_16.jpg', NOW(), NOW()),
                                                                                                                  (6, '남성 후드티 2', '스타일리시한 남성 후드티입니다.', 125, 42900.00, 'thumbnail_url_17.jpg', NOW(), NOW()),
                                                                                                                  (9, '여성 후드티 2', '스타일리시한 여성 후드티입니다.', 135, 42900.00, 'thumbnail_url_18.jpg', NOW(), NOW()),
                                                                                                                  (12, '아동 후드티 2', '스타일리시한 아동 후드티입니다.', 115, 22900.00, 'thumbnail_url_19.jpg', NOW(), NOW()),
                                                                                                                  (7, '여성 티셔츠 3', '캐주얼한 여성 티셔츠입니다.', 220, 15900.00, 'thumbnail_url_20.jpg', NOW(), NOW()),
                                                                                                                  (10, '아동 티셔츠 3', '캐주얼한 아동 티셔츠입니다.', 170, 11900.00, 'thumbnail_url_21.jpg', NOW(), NOW()),
                                                                                                                  (4, '남성 티셔츠 4', '캐주얼한 남성 티셔츠입니다.', 115, 15900.00, 'thumbnail_url_22.jpg', NOW(), NOW()),
                                                                                                                  (5, '남성 셔츠 3', '클래식한 남성 셔츠입니다.', 90, 47900.00, 'thumbnail_url_23.jpg', NOW(), NOW()),
                                                                                                                  (8, '여성 셔츠 3', '클래식한 여성 셔츠입니다.', 100, 47900.00, 'thumbnail_url_24.jpg', NOW(), NOW()),
                                                                                                                  (11, '아동 셔츠 3', '클래식한 아동 셔츠입니다.', 80, 23900.00, 'thumbnail_url_25.jpg', NOW(), NOW()),
                                                                                                                  (6, '남성 후드티 3', '유니크한 남성 후드티입니다.', 130, 44900.00, 'thumbnail_url_26.jpg', NOW(), NOW()),
                                                                                                                  (9, '여성 후드티 3', '유니크한 여성 후드티입니다.', 140, 44900.00, 'thumbnail_url_27.jpg', NOW(), NOW()),
                                                                                                                  (12, '아동 후드티 3', '유니크한 아동 후드티입니다.', 120, 24900.00, 'thumbnail_url_28.jpg', NOW(), NOW()),
                                                                                                                  (7, '여성 티셔츠 4', '스포티한 여성 티셔츠입니다.', 230, 14900.00, 'thumbnail_url_29.jpg', NOW(), NOW()),
                                                                                                                  (10, '아동 티셔츠 4', '스포티한 아동 티셔츠입니다.', 180, 10900.00, 'thumbnail_url_30.jpg', NOW(), NOW());

-- 제품 옵션 정보를 저장하는 INSERT문
INSERT INTO product_options (option_name, option_value, created_at, updated_at) VALUES
                                                                                    ('색상', '블랙', NOW(), NOW()),
                                                                                    ('색상', '화이트', NOW(), NOW()),
                                                                                    ('색상', '네이비', NOW(), NOW()),
                                                                                    ('색상', '그레이', NOW(), NOW()),
                                                                                    ('색상', '블루', NOW(), NOW()),
                                                                                    ('색상', '레드', NOW(), NOW()),
                                                                                    ('색상', '핑크', NOW(), NOW()),
                                                                                    ('색상', '옐로우', NOW(), NOW()),
                                                                                    ('사이즈', 'S', NOW(), NOW()),
                                                                                    ('사이즈', 'M', NOW(), NOW()),
                                                                                    ('사이즈', 'L', NOW(), NOW()),
                                                                                    ('사이즈', 'XL', NOW(), NOW());


-- 제품 옵션 조합 정보를 저장하는 INSERT문
INSERT INTO product_option_combination (product_id, combination_value, stock, price, created_at, updated_at) VALUES
                                                                                                                 (1, '색상: 블랙, 사이즈: S', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 블랙, 사이즈: M', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 블랙, 사이즈: L', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 블랙, 사이즈: XL', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 화이트, 사이즈: S', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 화이트, 사이즈: M', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 화이트, 사이즈: L', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (1, '색상: 화이트, 사이즈: XL', 25, 19900.00, NOW(), NOW()),
                                                                                                                 (2, '색상: 네이비, 사이즈: S', 37, 29900.00, NOW(), NOW()),
                                                                                                                 (2, '색상: 네이비, 사이즈: M', 38, 29900.00, NOW(), NOW()),
                                                                                                                 (2, '색상: 그레이, 사이즈: S', 37, 29900.00, NOW(), NOW()),
                                                                                                                 (2, '색상: 그레이, 사이즈: M', 38, 29900.00, NOW(), NOW()),
                                                                                                                 (3, '색상: 블루, 사이즈: M', 20, 49900.00, NOW(), NOW()),
                                                                                                                 (3, '색상: 블루, 사이즈: L', 20, 49900.00, NOW(), NOW()),
                                                                                                                 (3, '색상: 화이트, 사이즈: M', 20, 49900.00, NOW(), NOW()),
                                                                                                                 (3, '색상: 화이트, 사이즈: L', 20, 49900.00, NOW(), NOW()),
                                                                                                                 (4, '색상: 레드, 사이즈: L', 30, 39900.00, NOW(), NOW()),
                                                                                                                 (4, '색상: 레드, 사이즈: XL', 30, 39900.00, NOW(), NOW()),
                                                                                                                 (4, '색상: 블랙, 사이즈: L', 30, 39900.00, NOW(), NOW()),
                                                                                                                 (4, '색상: 블랙, 사이즈: XL', 30, 39900.00, NOW(), NOW()),
                                                                                                                 (5, '색상: 핑크, 사이즈: S', 50, 19900.00, NOW(), NOW()),
                                                                                                                 (5, '색상: 핑크, 사이즈: M', 50, 19900.00, NOW(), NOW()),
                                                                                                                 (5, '색상: 옐로우, 사이즈: S', 50, 19900.00, NOW(), NOW()),
                                                                                                                 (5, '색상: 옐로우, 사이즈: M', 50, 19900.00, NOW(), NOW());

-- 제품 옵션 조합 상세 정보를 저장하는 INSERT문
INSERT INTO combination_detail (combination_id, option_id, created_at, updated_at)
SELECT pc.combination_id, po.option_id, NOW(), NOW()
FROM product_option_combination pc
         JOIN product_options po ON pc.combination_value LIKE CONCAT('%', po.option_name, ': ', po.option_value, '%');


-- 제품 detail 이미지 정보를 저장하는 INSERT 문
INSERT INTO Product_Images (product_Id, image_Url, is_Thumbnail, is_Detail_Image, is_Preview_Image, order_Index, created_At, updated_At) VALUES
                                                                                                                                             (1, 'detail_image_url_1.jpg', 0, 1, 0, 1, NOW(), NOW()),
                                                                                                                                             (1, 'detail_image_url_2.jpg', 0, 1, 0, 2, NOW(), NOW()),
                                                                                                                                             (1, 'detail_image_url_3.jpg', 0, 1, 0, 3, NOW(), NOW()),
                                                                                                                                             (1, 'detail_image_url_4.jpg', 0, 1, 0, 4, NOW(), NOW()),
                                                                                                                                             (1, 'detail_image_url_5.jpg', 0, 1, 0, 5, NOW(), NOW()),
                                                                                                                                             (2, 'detail_image_url_1.jpg', 0, 1, 0, 1, NOW(), NOW()),
                                                                                                                                             (2, 'detail_image_url_2.jpg', 0, 1, 0, 2, NOW(), NOW()),
                                                                                                                                             (2, 'detail_image_url_3.jpg', 0, 1, 0, 3, NOW(), NOW()),
                                                                                                                                             (2, 'detail_image_url_4.jpg', 0, 1, 0, 4, NOW(), NOW()),
                                                                                                                                             (2, 'detail_image_url_5.jpg', 0, 1, 0, 5, NOW(), NOW()),
                                                                                                                                             (3, 'detail_image_url_1.jpg', 0, 1, 0, 1, NOW(), NOW()),
                                                                                                                                             (3, 'detail_image_url_2.jpg', 0, 1, 0, 2, NOW(), NOW()),
                                                                                                                                             (3, 'detail_image_url_3.jpg', 0, 1, 0, 3, NOW(), NOW()),
                                                                                                                                             (3, 'detail_image_url_4.jpg', 0, 1, 0, 4, NOW(), NOW()),
                                                                                                                                             (3, 'detail_image_url_5.jpg', 0, 1, 0, 5, NOW(), NOW()),
                                                                                                                                             (4, 'detail_image_url_1.jpg', 0, 1, 0, 1, NOW(), NOW()),
                                                                                                                                             (4, 'detail_image_url_2.jpg', 0, 1, 0, 2, NOW(), NOW()),
                                                                                                                                             (4, 'detail_image_url_3.jpg', 0, 1, 0, 3, NOW(), NOW()),
                                                                                                                                             (4, 'detail_image_url_4.jpg', 0, 1, 0, 4, NOW(), NOW()),
                                                                                                                                             (4, 'detail_image_url_5.jpg', 0, 1, 0, 5, NOW(), NOW()),
                                                                                                                                             (5, 'detail_image_url_1.jpg', 0, 1, 0, 1, NOW(), NOW()),
                                                                                                                                             (5, 'detail_image_url_2.jpg', 0, 1, 0, 2, NOW(), NOW()),
                                                                                                                                             (5, 'detail_image_url_3.jpg', 0, 1, 0, 3, NOW(), NOW()),
                                                                                                                                             (5, 'detail_image_url_4.jpg', 0, 1, 0, 4, NOW(), NOW()),
                                                                                                                                             (5, 'detail_image_url_5.jpg', 0, 1, 0, 5, NOW(), NOW());

-- 제품 preview 이미지 정보를 저장하는 INSERT 문
INSERT INTO Product_Images (product_Id, image_Url, is_Thumbnail, is_Detail_Image, is_Preview_Image, order_Index, created_At, updated_At) VALUES
                                                                                                                                             (1, 'preview_image_url_1.jpg', 0, 0, 1, 1, NOW(), NOW()),
                                                                                                                                             (1, 'preview_image_url_2.jpg', 0, 0, 1, 2, NOW(), NOW()),
                                                                                                                                             (1, 'preview_image_url_3.jpg', 0, 0, 1, 3, NOW(), NOW()),
                                                                                                                                             (1, 'preview_image_url_4.jpg', 0, 0, 1, 4, NOW(), NOW()),
                                                                                                                                             (1, 'preview_image_url_5.jpg', 0, 0, 1, 5, NOW(), NOW()),
                                                                                                                                             (2, 'preview_image_url_1.jpg', 0, 0, 1, 1, NOW(), NOW()),
                                                                                                                                             (2, 'preview_image_url_2.jpg', 0, 0, 1, 2, NOW(), NOW()),
                                                                                                                                             (2, 'preview_image_url_3.jpg', 0, 0, 1, 3, NOW(), NOW()),
                                                                                                                                             (2, 'preview_image_url_4.jpg', 0, 0, 1, 4, NOW(), NOW()),
                                                                                                                                             (2, 'preview_image_url_5.jpg', 0, 0, 1, 5, NOW(), NOW()),
                                                                                                                                             (3, 'preview_image_url_1.jpg', 0, 0, 1, 1, NOW(), NOW()),
                                                                                                                                             (3, 'preview_image_url_2.jpg', 0, 0, 1, 2, NOW(), NOW()),
                                                                                                                                             (3, 'preview_image_url_3.jpg', 0, 0, 1, 3, NOW(), NOW()),
                                                                                                                                             (3, 'preview_image_url_4.jpg', 0, 0, 1, 4, NOW(), NOW()),
                                                                                                                                             (3, 'preview_image_url_5.jpg', 0, 0, 1, 5, NOW(), NOW()),
                                                                                                                                             (4, 'preview_image_url_1.jpg', 0, 0, 1, 1, NOW(), NOW()),
                                                                                                                                             (4, 'preview_image_url_2.jpg', 0, 0, 1, 2, NOW(), NOW()),
                                                                                                                                             (4, 'preview_image_url_3.jpg', 0, 0, 1, 3, NOW(), NOW()),
                                                                                                                                             (4, 'preview_image_url_4.jpg', 0, 0, 1, 4, NOW(), NOW()),
                                                                                                                                             (4, 'preview_image_url_5.jpg', 0, 0, 1, 5, NOW(), NOW()),
                                                                                                                                             (5, 'preview_image_url_1.jpg', 0, 0, 1, 1, NOW(), NOW()),
                                                                                                                                             (5, 'preview_image_url_2.jpg', 0, 0, 1, 2, NOW(), NOW()),
                                                                                                                                             (5, 'preview_image_url_3.jpg', 0, 0, 1, 3, NOW(), NOW()),
                                                                                                                                             (5, 'preview_image_url_4.jpg', 0, 0, 1, 4, NOW(), NOW()),
                                                                                                                                             (5, 'preview_image_url_5.jpg', 0, 0, 1, 5, NOW(), NOW());

