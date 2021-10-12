-- update product
alter table product
    add product_description varchar(255) null;

-- MCRQA-3192 - 최병현
-- [세금계산서발급] 일련번호 채번 기준 이상
-- -- 세금계산서 마스터 ISSUE_DE PK로 변경
-- ALTER TABLE `MARKETBOM2_SCHM`.`BIZ_TAX_BILL_PUBLISH`
--     CHANGE COLUMN `ISSUE_DE` `ISSUE_DE` VARCHAR(8) NOT NULL AFTER `BIZ_CD`,
-- DROP PRIMARY KEY,
-- ADD PRIMARY KEY (`BIZ_GROUP_NO`, `BIZ_CD`, `ISSUE_DE`,`TAX_BILL_SEQ`);
--
-- -- 세금계산서 상세테이블 ISSUE_DE 컬럼 생성 및 PK추가
-- ALTER TABLE `MARKETBOM2_SCHM`.`BIZ_TAX_BILL_PUBLISH_DTL`
--     ADD COLUMN `ISSUE_DE` VARCHAR(8) NOT NULL AFTER `BIZ_CD`,
-- DROP PRIMARY KEY,
-- ADD PRIMARY KEY (`BIZ_GROUP_NO`, `BIZ_CD`,`ISSUE_DE`, `TAX_BILL_SEQ`, `ITEM_SEQ`);
--
-- -- 세금계산서 상세테이블 ISSUE_DE 데이터 세팅
-- update BIZ_TAX_BILL_PUBLISH_DTL
-- set ISSUE_DE = date_format(REG_DT, '%Y%m%d')
-- where ISSUE_DE = '' or ISSUE_DE is null;


-- MCRQA-9999
-- ???????? 업무 해결
-- -- 세금계산서 마스터 ISSUE_DE PK로 변경
-- ALTER TABLE `MARKETBOM2_SCHM`.`BIZ_TAX_BILL_PUBLISH`
--     CHANGE COLUMN `ISSUE_DE` `ISSUE_DE` VARCHAR(8) NOT NULL AFTER `BIZ_CD`,
-- DROP PRIMARY KEY,
-- ADD PRIMARY KEY (`BIZ_GROUP_NO`, `BIZ_CD`, `ISSUE_DE`,`TAX_BILL_SEQ`);
