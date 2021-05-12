package com.arupakaman.kawa

/*

TODO:
Issues
- need to destroy native ads to eradicate memory leaks
- first time app installed, opening an item just after the ad, in koan detail ad is opened instead of koan
    check In Dreamland
-

Short notes on data normalization:
What?
- normalization is a technique to reduce data redundancy, note that we can reduce it not eliminate

Why?
- normalization causes more disk space as we have different but related data occurring multiple times
- also there are insertion, updation and deletion anamoly

1NF
4 rules-
1. no duplication in column name for single table
2. the order of rows insertion should not affect the database in any way
3. the data entered in cell should be valid
4. each cell must contain atomic value (no comma separated values)

2NF
rules
- table should be in 1NF
- there should not be any partial dependency in any table

3NF
- table should be in 2NF
- there should not be any transitive dependency in any table

BCNF (3.5 NF)
- table should be in 3NF
- a member of prime attribute should not depend on non prime attribute, for ex;
enrollment table- name, subject, professor
primary key = name+subject
professor depends on name+subject
subject is part of primary key but depends on professor

4NF
- the table should be in BCNF
- there should be no multi value dependency



Issues:
1. when changing the theme share dialog appearing again and again
2. 


Need to discuss
1. Is all the images have same aspect ratio, what it is?  wrap_content
2. fonts which one where to use
Baskerville- cell, title, drawer, search
detail kons: SawarabiMincho
about krishna in japanaese: SatsukiGendaiMincho


3. In koan cell item, what is the number?


4. In search screen the filled dot is to clear, right?
no it is for search query to trigger but we can use it for clear

5. In search result there may be chances that the result matched with title and koans, what we will do in that case?
In case of card UI we need to search with title and in case of list with koan

- In search when type japan it shows search result but when type japane, it doesn't show anything
- system default dark mode enable on app start
- image changes when getting back to the now screen
 */