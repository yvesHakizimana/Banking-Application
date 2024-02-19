--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- Name: count_accounts(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.count_accounts() RETURNS TABLE(savings_count integer, current_count integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Declare variables
    savings_count := 0;
    current_count := 0;

    -- Query to count savings and current accounts
    SELECT
        COUNT(CASE WHEN account_type = 'saving' THEN 1 END),
        COUNT(CASE WHEN account_type = 'current' THEN 1 END)
    INTO
        savings_count,
        current_count
    FROM account;

    -- Return the counts
    RETURN NEXT;
END;
$$;


ALTER FUNCTION public.count_accounts() OWNER TO postgres;

--
-- Name: create_bank_account(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.create_bank_account() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin 
insert into Account (account_id , customer_id  , account_type, balance, last_withdrawal_date)
values (uuid_generate_v4(), NEW.customer_id , NEW.accounttype, 0.0, NULL);
return new;
end;
$$;


ALTER FUNCTION public.create_bank_account() OWNER TO postgres;

--
-- Name: get_accounts_created(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_accounts_created() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    total_accounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_accounts
    FROM account;
    RETURN total_accounts;
END;
$$;


ALTER FUNCTION public.get_accounts_created() OWNER TO postgres;

--
-- Name: get_customer_account_info(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_customer_account_info() RETURNS TABLE(first_name character varying, last_name character varying, username character varying, age integer, phonenumber integer, account_id uuid, account_type character varying, balance numeric)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT c.first_name, c.last_name, c.username, c.age, c.phonenumber, a.account_id, a.account_type, a.balance
    FROM customer c
    JOIN account a USING (customer_id);
END;
$$;


ALTER FUNCTION public.get_customer_account_info() OWNER TO postgres;

--
-- Name: get_total_balance(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_total_balance() RETURNS numeric
    LANGUAGE plpgsql
    AS $$
DECLARE
    total_balance NUMERIC;
BEGIN
    SELECT SUM(balance) INTO total_balance
    FROM account;

    RETURN total_balance;
END;
$$;


ALTER FUNCTION public.get_total_balance() OWNER TO postgres;

--
-- Name: get_total_deposits(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_total_deposits() RETURNS numeric
    LANGUAGE plpgsql
    AS $$
DECLARE
    total_deposits NUMERIC;
BEGIN
    SELECT SUM(amount) INTO total_deposits
    FROM transaction
    WHERE transaction_type = 'Deposit';

    RETURN total_deposits;
END;
$$;


ALTER FUNCTION public.get_total_deposits() OWNER TO postgres;

--
-- Name: get_total_withdrawals(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_total_withdrawals() RETURNS numeric
    LANGUAGE plpgsql
    AS $$
DECLARE
    total_withdrawals NUMERIC;
BEGIN
    SELECT SUM(amount) INTO total_withdrawals
    FROM transaction
    WHERE transaction_type = 'Withdrawal';

    RETURN total_withdrawals;
END;
$$;


ALTER FUNCTION public.get_total_withdrawals() OWNER TO postgres;

--
-- Name: group_transactions(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.group_transactions() RETURNS TABLE(transaction_type text, total_amount numeric)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY 
    SELECT transaction.transaction_type::text, SUM(transaction.amount)::numeric
    FROM transaction
    GROUP BY transaction.transaction_type;
END;
$$;


ALTER FUNCTION public.group_transactions() OWNER TO postgres;

--
-- Name: group_transactions(date, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.group_transactions(start_date date, end_date date) RETURNS TABLE(transaction_type text, total_amount numeric)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
        SELECT transaction.transaction_type, SUM(transaction.amount)
        FROM transaction
        WHERE transaction.transaction_date >= start_date
          AND transaction.transaction_date <= end_date
        GROUP BY transaction.transaction_type;
END;
$$;


ALTER FUNCTION public.group_transactions(start_date date, end_date date) OWNER TO postgres;

--
-- Name: insert_audit_after_insert_account(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.insert_audit_after_insert_account() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
insert into transaction (account_id, customer_id, transaction_type, amount, transaction_date, account_type)
values (NEW.account_id, NEW.customer_id, 'account_created', 0, CURRENT_TIMESTAMP, NEW.account_type);
return NEW;
END;
$$;


ALTER FUNCTION public.insert_audit_after_insert_account() OWNER TO postgres;

--
-- Name: register_user_with_image(integer, character varying, character varying, character varying, character varying, integer, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.register_user_with_image(IN p_age integer, IN p_email character varying, IN p_fname character varying, IN p_lname character varying, IN p_password character varying, IN p_phonenumber integer, IN p_account_type character varying, IN p_filepath character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE
    user_id INT;
BEGIN
    -- Insert user data and retrieve the generated ID
    INSERT INTO customer(age, username, first_name, last_name, password, phoneNumber, accounttype)
    VALUES (p_age, p_email, p_fname, p_lname, p_password, p_phoneNumber, p_account_type)
    RETURNING customer_id INTO user_id;

    -- Insert image path associated with the user ID
    INSERT INTO customer_images(customer_id, file_path)
    VALUES (user_id, p_filePath);
END;
$$;


ALTER PROCEDURE public.register_user_with_image(IN p_age integer, IN p_email character varying, IN p_fname character varying, IN p_lname character varying, IN p_password character varying, IN p_phonenumber integer, IN p_account_type character varying, IN p_filepath character varying) OWNER TO postgres;

--
-- Name: update_transaction(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_transaction() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.balance > OLD.balance THEN
        INSERT INTO transaction (account_id, customer_id, transaction_type, amount, transaction_date, account_type)
        VALUES (NEW.account_id, NEW.customer_id, 'Deposit', NEW.balance - OLD.balance, NOW(), NEW.account_type);
    ELSE
        INSERT INTO transaction(account_id, customer_id, transaction_type, amount, transaction_date, account_type)
        VALUES (NEW.account_id, NEW.customer_id, 'Withdrawal', OLD.balance - NEW.balance, NOW(), NEW.account_type);
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_transaction() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    account_id uuid NOT NULL,
    customer_id integer,
    account_type character varying(20),
    balance numeric(10,2),
    last_withdrawal_date timestamp without time zone
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: admin_table; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin_table (
    id integer NOT NULL,
    username character varying(50),
    password character varying(50)
);


ALTER TABLE public.admin_table OWNER TO postgres;

--
-- Name: admin_table_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.admin_table_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.admin_table_id_seq OWNER TO postgres;

--
-- Name: admin_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.admin_table_id_seq OWNED BY public.admin_table.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    age integer,
    username character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    phonenumber integer,
    accounttype character varying(20)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.customer ALTER COLUMN customer_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.customer_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: customer_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_images (
    id integer NOT NULL,
    customer_id integer,
    file_path character varying(255) NOT NULL
);


ALTER TABLE public.customer_images OWNER TO postgres;

--
-- Name: customer_images_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_images_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_images_id_seq OWNER TO postgres;

--
-- Name: customer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_images_id_seq OWNED BY public.customer_images.id;


--
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    transaction_id integer NOT NULL,
    account_id uuid,
    customer_id integer,
    transaction_type character varying(20),
    amount numeric(10,2),
    transaction_date timestamp without time zone,
    account_type character varying(20) DEFAULT 'regular'::character varying NOT NULL
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transaction_transaction_id_seq OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_transaction_id_seq OWNED BY public.transaction.transaction_id;


--
-- Name: admin_table id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_table ALTER COLUMN id SET DEFAULT nextval('public.admin_table_id_seq'::regclass);


--
-- Name: customer_images id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_images ALTER COLUMN id SET DEFAULT nextval('public.customer_images_id_seq'::regclass);


--
-- Name: transaction transaction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction ALTER COLUMN transaction_id SET DEFAULT nextval('public.transaction_transaction_id_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account (account_id, customer_id, account_type, balance, last_withdrawal_date) FROM stdin;
79a15b64-a8d7-4b6e-9426-781eadf550f8	2	current	8000.00	2024-02-15 15:06:42.941
c994a291-8439-4143-96ba-29d259f0220e	6	current	0.00	\N
68c11c54-e6fa-4acd-9050-fa463f71f93b	5	saving	8000.00	2024-02-15 15:00:10.553
16b4a8db-8076-47cc-afc7-d273978038dd	1	current	0.00	\N
53552a64-cbfa-4264-882c-cc7114d8bf6d	3	current	0.00	\N
79f23b69-58b4-41d2-a01b-c1cbfcbb7b72	4	saving	20000.00	\N
\.


--
-- Data for Name: admin_table; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.admin_table (id, username, password) FROM stdin;
1	admin	yvesknight
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (customer_id, age, username, first_name, last_name, password, phonenumber, accounttype) FROM stdin;
1	23	knight@gmail.com	Yves	HAKIZIMANA	bc49a0653d8fd144e9712366ac865bbf6828adacddc69af88c9f397c3c424e0aa2e5d6e31b7c01c22c4faa395a068563247ae94633bf339ead7b11ea7af5ff7b	783520475	current
2	23	sammy@gmail.com	Sammy	Kalinda	9ff7081080519852e81257cf4f743938c4a936ccfd5d7e91a75102254b79c2e4054fc93dc90e87b184534a9a2aee78a6c54fb0103e81aa10cfa23e81cda82c4a	789453672	current
3	23	broony@gmail.com	Yves	Mugisha	bc9af61c051568b647da43cb27530161a2368996cdd3873202e93d89346cc12179288fb795a5f344326debb49c6071912231b6cfccd0bdda783ad9c8bcb29ff7	786564321	current
4	23	chiesa014@gmail.com	Chiesa	UWIMANA	99ed408d66dff0f3d64f9753dc60c1a157f2bd1b2682cd6d84d0a4c4558fee193704f1ab4197d0355e67407f3613391d35f190354e74739b823953e99ebbdc9f	786345672	saving
5	23	bellariane@gmail.com	Bella	Ariane	9a9397d2d7f6f63b8591b49b714ce9bc4b65500d746234012ca0c316e30b2a1afd633bedb702c58a022eda0c9ea30ac1d5a1591c39344ebb4f003ff864bae79d	786574563	saving
6	43	pascal108@gmail.com	Pascal	HAKIZIMANA	9ff7081080519852e81257cf4f743938c4a936ccfd5d7e91a75102254b79c2e4054fc93dc90e87b184534a9a2aee78a6c54fb0103e81aa10cfa23e81cda82c4a	788570829	current
\.


--
-- Data for Name: customer_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer_images (id, customer_id, file_path) FROM stdin;
1	1	/CustomerImages/383b5725-0520-44c8-b868-8916dbaf1caf.png
2	2	/CustomerImages/bd1c7c5b-9c17-4721-8c4a-9be2b01b08ab.png
3	3	/CustomerImages/bc6785cc-08c6-483e-bfcc-b4f0a245f5fc.jpg
4	4	/CustomerImages/38a38e91-2778-49ba-8edc-955fa9c5259d.png
5	5	/CustomerImages/5f4affc1-7164-4137-88fd-b6e206a1d4b5.jpg
6	6	/CustomerImages/cf39f2ab-8b34-40c6-b849-9d0262556040.png
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (transaction_id, account_id, customer_id, transaction_type, amount, transaction_date, account_type) FROM stdin;
1	16b4a8db-8076-47cc-afc7-d273978038dd	1	account_created	0.00	2024-02-15 08:02:03.857802	current
2	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	account_created	0.00	2024-02-15 08:04:41.243885	current
3	53552a64-cbfa-4264-882c-cc7114d8bf6d	3	account_created	0.00	2024-02-15 08:41:11.224624	current
4	79f23b69-58b4-41d2-a01b-c1cbfcbb7b72	4	account_created	0.00	2024-02-15 09:33:00.848917	saving
5	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Deposit	2000.00	2024-02-15 13:37:26.92615	current
6	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Deposit	1000.00	2024-02-15 13:37:42.574166	current
7	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Deposit	2000.00	2024-02-15 14:03:31.545761	current
8	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Withdrawal	1000.00	2024-02-15 14:13:36.903968	current
9	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Withdrawal	1000.00	2024-02-15 14:42:17.836795	current
10	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Deposit	2000.00	2024-02-15 14:46:30.943687	current
11	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Deposit	1200.00	2024-02-15 14:56:47.261042	current
12	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Withdrawal	2200.00	2024-02-15 14:56:55.104243	current
13	68c11c54-e6fa-4acd-9050-fa463f71f93b	5	account_created	0.00	2024-02-15 14:58:37.713684	saving
14	68c11c54-e6fa-4acd-9050-fa463f71f93b	5	Deposit	10000.00	2024-02-15 14:58:53.301206	saving
15	68c11c54-e6fa-4acd-9050-fa463f71f93b	5	Withdrawal	2000.00	2024-02-15 14:59:58.857856	saving
16	68c11c54-e6fa-4acd-9050-fa463f71f93b	5	Withdrawal	1000.00	2024-02-15 15:00:10.564577	saving
17	79a15b64-a8d7-4b6e-9426-781eadf550f8	2	Withdrawal	2000.00	2024-02-15 15:06:42.967092	current
18	c994a291-8439-4143-96ba-29d259f0220e	6	account_created	0.00	2024-02-15 16:27:48.013319	current
19	68c11c54-e6fa-4acd-9050-fa463f71f93b	5	Deposit	1000.00	2024-02-16 08:26:20.698878	saving
\.


--
-- Name: admin_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.admin_table_id_seq', 1, true);


--
-- Name: customer_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_customer_id_seq', 6, true);


--
-- Name: customer_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_images_id_seq', 6, true);


--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_transaction_id_seq', 19, true);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_id);


--
-- Name: admin_table admin_table_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_table
    ADD CONSTRAINT admin_table_pkey PRIMARY KEY (id);


--
-- Name: customer_images customer_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_images
    ADD CONSTRAINT customer_images_pkey PRIMARY KEY (id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id);


--
-- Name: customer uk_mufchskagt7e1w4ksmt9lum5l; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT uk_mufchskagt7e1w4ksmt9lum5l UNIQUE (username);


--
-- Name: account account_balance_update_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER account_balance_update_trigger AFTER UPDATE ON public.account FOR EACH ROW EXECUTE FUNCTION public.update_transaction();


--
-- Name: account after_insert_account; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER after_insert_account AFTER INSERT ON public.account FOR EACH ROW EXECUTE FUNCTION public.insert_audit_after_insert_account();


--
-- Name: customer after_insert_user; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER after_insert_user AFTER INSERT ON public.customer FOR EACH ROW EXECUTE FUNCTION public.create_bank_account();


--
-- Name: customer_images customer_images_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_images
    ADD CONSTRAINT customer_images_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: transaction transaction_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id);


--
-- PostgreSQL database dump complete
--

