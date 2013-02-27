# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table authorised_user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  activated                 boolean,
  email_change_id           bigint,
  constraint pk_authorised_user primary key (id))
;

create table email_change_request (
  id                        bigint not null,
  secret_code               varchar(255),
  email                     varchar(255),
  created_at                timestamp,
  valid_to                  timestamp,
  constraint pk_email_change_request primary key (id))
;

create table forgot_password_request (
  id                        bigint not null,
  authorised_user_id        bigint not null,
  access_code               varchar(255),
  created_at                timestamp,
  valid_to                  timestamp,
  constraint pk_forgot_password_request primary key (id))
;

create table security_role (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_security_role primary key (id))
;

create table user_confirmation_request (
  id                        bigint not null,
  authorised_user_id        bigint not null,
  access_code               varchar(255),
  activation_code           varchar(255),
  constraint pk_user_confirmation_request primary key (id))
;

create table user_permission (
  id                        bigint not null,
  permission_value          varchar(255),
  constraint pk_user_permission primary key (id))
;

create table user_session (
  id                        bigint not null,
  user_id                   bigint,
  login_secret              varchar(1500) not null,
  due                       timestamp,
  host                      varchar(255),
  constraint pk_user_session primary key (id))
;


create table authorised_user_security_role (
  authorised_user_id             bigint not null,
  security_role_id               bigint not null,
  constraint pk_authorised_user_security_role primary key (authorised_user_id, security_role_id))
;

create table authorised_user_user_permission (
  authorised_user_id             bigint not null,
  user_permission_id             bigint not null,
  constraint pk_authorised_user_user_permission primary key (authorised_user_id, user_permission_id))
;
create sequence authorised_user_seq;

create sequence email_change_request_seq;

create sequence forgot_password_request_seq;

create sequence security_role_seq;

create sequence user_confirmation_request_seq;

create sequence user_permission_seq;

create sequence user_session_seq;

alter table authorised_user add constraint fk_authorised_user_emailChange_1 foreign key (email_change_id) references email_change_request (id) on delete restrict on update restrict;
create index ix_authorised_user_emailChange_1 on authorised_user (email_change_id);
alter table forgot_password_request add constraint fk_forgot_password_request_aut_2 foreign key (authorised_user_id) references authorised_user (id) on delete restrict on update restrict;
create index ix_forgot_password_request_aut_2 on forgot_password_request (authorised_user_id);
alter table user_confirmation_request add constraint fk_user_confirmation_request_a_3 foreign key (authorised_user_id) references authorised_user (id) on delete restrict on update restrict;
create index ix_user_confirmation_request_a_3 on user_confirmation_request (authorised_user_id);
alter table user_session add constraint fk_user_session_user_4 foreign key (user_id) references authorised_user (id) on delete restrict on update restrict;
create index ix_user_session_user_4 on user_session (user_id);



alter table authorised_user_security_role add constraint fk_authorised_user_security_r_01 foreign key (authorised_user_id) references authorised_user (id) on delete restrict on update restrict;

alter table authorised_user_security_role add constraint fk_authorised_user_security_r_02 foreign key (security_role_id) references security_role (id) on delete restrict on update restrict;

alter table authorised_user_user_permission add constraint fk_authorised_user_user_permi_01 foreign key (authorised_user_id) references authorised_user (id) on delete restrict on update restrict;

alter table authorised_user_user_permission add constraint fk_authorised_user_user_permi_02 foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists authorised_user;

drop table if exists authorised_user_security_role;

drop table if exists authorised_user_user_permission;

drop table if exists email_change_request;

drop table if exists forgot_password_request;

drop table if exists security_role;

drop table if exists user_confirmation_request;

drop table if exists user_permission;

drop table if exists user_session;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists authorised_user_seq;

drop sequence if exists email_change_request_seq;

drop sequence if exists forgot_password_request_seq;

drop sequence if exists security_role_seq;

drop sequence if exists user_confirmation_request_seq;

drop sequence if exists user_permission_seq;

drop sequence if exists user_session_seq;

