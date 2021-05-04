-- SAMPLE DATA for Auction (auction id: 247)
-- BID ID starts from 100
-- AUCTION for product 'computer'
-- INSERT into AUCTION (auction_id, begin_date, end_date, auction_status, begin_price, winner_bid_id, shipping_date, shipping_status)
--                     values (247, '2020-12-06 15:00:00', '2020-12-15 15:00:00', 1, 100 , null, null, null);
--
-- INSERT into BID (ID, AMOUNT, BIDDING_TIME) values (101, 120, '2020-12-06 17:00:00');
-- INSERT into BID (ID, AMOUNT, BIDDING_TIME) values (102, 130, '2020-12-06 17:30:00');
-- INSERT into BID (ID, AMOUNT, BIDDING_TIME) values (103, 140, '2020-12-06 18:00:00');
-- INSERT into BID (ID, AMOUNT, BIDDING_TIME) values (104, 150, '2020-12-06 19:00:00');
--
-- INSERT into USERS_BIDS (USER_USER_ID, BIDS_ID) values (1, 101);
-- INSERT into USERS_BIDS (USER_USER_ID, BIDS_ID) values (2, 102);
-- INSERT into USERS_BIDS (USER_USER_ID, BIDS_ID) values (1, 103);
-- INSERT into USERS_BIDS (USER_USER_ID, BIDS_ID) values (3, 104);
--
-- INSERT into AUCTION_BID_ID (AUCTION_AUCTION_ID, BID_ID_ID) values (247, 101);
-- INSERT into AUCTION_BID_ID (AUCTION_AUCTION_ID, BID_ID_ID) values (247, 102);
-- INSERT into AUCTION_BID_ID (AUCTION_AUCTION_ID, BID_ID_ID) values (247, 103);
-- INSERT into AUCTION_BID_ID (AUCTION_AUCTION_ID, BID_ID_ID) values (247, 104);









