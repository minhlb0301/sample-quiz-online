DROP DATABASE QuizOnline

CREATE DATABASE QuizOnline

USE QuizOnline

CREATE TABLE Account(
Email		varchar(50)		primary key,
[Password]	varchar(MAX)	,
Name		nvarchar(MAX)	,
RoleId		varchar(10)		,
[Status]	bit				
)

CREATE TABLE [Role](
RoleId		varchar(10)		primary key,
RoleName	varchar(15)		,
)

CREATE TABLE [Subject](
SubjectId	varchar(50)		primary key,
SubjectName	varchar(MAX)	,
NumberOfQuestion	int		,
Email		varchar(50)		
)

CREATE TABLE Question(
QuestionId		varchar(50)		primary key,
SubjectId		varchar(50)		,
Question_Content	varchar(MAX)	,
CreateDate		datetime		,
[Status]		bit				,
Email			varchar(50)		
)

CREATE TABLE Answer(
AnswerId		varchar(50)		primary key,
QuestionId		varchar(50)		,
Answer			varchar(MAX)	,
IsCorrect		bit				,
CreateDate		datetime
)

CREATE TABLE Result(
ResultId		varchar(50)		primary key,
Email			varchar(50)		,
SubjectId		varchar(50)		,
CreateDate		datetime		,
NumberOfCorrect	varchar(10)		,
Point			float			check (Point >= 0 and Point <= 10)	
)



ALTER TABLE Account
ADD CONSTRAINT Acc_Role
FOREIGN KEY (RoleId)
REFERENCES [Role](RoleId)

ALTER TABLE [Subject]
ADD CONSTRAINT Acc_Subject
FOREIGN KEY (Email)
REFERENCES Account(Email)

ALTER TABLE Question
ADD CONSTRAINT Acc_Question
FOREIGN KEY (Email)
REFERENCES Account(Email)

ALTER TABLE Result
ADD CONSTRAINT Acc_Result
FOREIGN KEY (Email)
REFERENCES Account(Email)

ALTER TABLE Question
ADD CONSTRAINT Subject_Question
FOREIGN KEY (SubjectId)
REFERENCES [Subject](SubjectId)

ALTER TABLE Answer
ADD CONSTRAINT Question_Answer
FOREIGN KEY (QuestionId)
REFERENCES	Question(QuestionId)

ALTER TABLE Result
ADD CONSTRAINT Subject_Result
FOREIGN KEY (SubjectId)
REFERENCES [Subject](SubjectId)

INSERT INTO Account(Email, [Password], Name, RoleId, [Status])
VALUES	('admin@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'Lương Bội Minh', '1', 'True'), 
		('student@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', N'Nguyễn Văn A', '2', 'True')
    /*    admin@gmail.com, 123456
		  student@gmail.com, 123   */

INSERT INTO [Role](RoleId, RoleName)
VALUES  ('1','Admin'),
		('2','Student')

INSERT INTO [Subject] (SubjectId, SubjectName, NumberOfQuestion, Email)
VALUES  ('PRJ321','Java Web Application','10','admin@gmail.com'),
		('SWR302','Software Requirement','10','admin@gmail.com')


INSERT INTO Question (QuestionId, SubjectId, Question_Content, CreateDate, [Status], Email)
VALUES  ('PRJ321_1', 'PRJ321','Which of these is legal attribute of page directive?', '2021-02-16 01:00:00', 'true', 'admin@gmail.com'),
		('PRJ321_2','PRJ321','Which of the following implicit variables should be used by a jsp page to access a resource and to forward a request to another jsp page?','2021-02-16 02:00:00','TRUE','admin@gmail.com'),
		('PRJ321_3','PRJ321','You need to modify deployment descriptor for a web application insalled in MyWebApp directory. Which file would you look for?','2021-02-16 03:00:00','TRUE','admin@gmail.com'),
		('PRJ321_4','PRJ321','EJB 3.0 specifications are first implemented in ______','2021-02-16 04:00:00','TRUE','admin@gmail.com'),
		('PRJ321_5','PRJ321','For JSP scopes of request and page, what are the classes of objects that are used to store the attributes?','2021-02-16 05:00:00','TRUE','admin@gmail.com'),
		('PRJ321_6','PRJ321','The form attribute _____specifies the server-side form handler, i.e., the program that handles the request','2021-02-16 06:00:00','TRUE','admin@gmail.com'),
		('PRJ321_7','PRJ321','Which HTTP method is used in FORM based Authentication?','2021-02-16 07:00:00','TRUE','admin@gmail.com'),
		('PRJ321_8','PRJ321','Consider the following HTML page code. Which method will be called on UploaderServlet when a user ....','2021-02-16 08:00:00','TRUE','admin@gmail.com'),
		('PRJ321_9','PRJ321','______________is the well-known host name that refers to your own computer.','2021-02-16 09:00:00','TRUE','admin@gmail.com'),
		('PRJ321_10','PRJ321','Identify the method used to get an object available in a session','2021-02-16 10:00:00','TRUE','admin@gmail.com'),
		('PRJ321_11','PRJ321','A Java developer needs to be able to send email, containing XML attachments,using SMTP. Which JEE (J2EE) technology provides this capability?','2021-02-16 11:00:00','TRUE','admin@gmail.com'),
		('PRJ321_12','PRJ321','Which is NOT a core component of JSP?','2021-02-16 12:00:00','TRUE','admin@gmail.com'),
		('PRJ321_13','PRJ321','Which technology is used for proccessing HTTP requests and mapping those requests to business objects','2021-02-16 13:00:00','TRUE','admin@gmail.com'),
		('PRJ321_14','PRJ321','What would be the best directory in which to store a supporting JAR file for a web application? Note that in the list below, all directories begin from the context root','2021-02-16 14:00:00','TRUE','admin@gmail.com'),
		('PRJ321_15','PRJ321','To send binary output in a response, the following method of HttpServletResponse may be used to get the appropriate Writer/Stream object.','2021-02-16 15:00:00','TRUE','admin@gmail.com')
		
INSERT INTO Answer(AnswerId, QuestionId, Answer, IsCorrect, CreateDate)
VALUES	('Answer_1','PRJ321_1','include','false','2021-02-16 01:01:00'),
		('Answer_2','PRJ321_1','scope','false','2021-02-16 01:02:00'),
		('Answer_3','PRJ321_1','debug','false','2021-02-16 01:03:00'),
		('Answer_4','PRJ321_1','errorPage','true','2021-02-16 01:04:00'),
		('Answer_5','PRJ321_2','config for both','false','2021-02-16 01:05:00'),
		('Answer_6','PRJ321_2','application and config','false','2021-02-16 01:06:00'),
		('Answer_7','PRJ321_2','config and pageContext','false','2021-02-16 01:07:00'),
		('Answer_8','PRJ321_2','application for both','true','2021-02-16 01:08:00'),
		('Answer_9','PRJ321_3','web.xml in MyWebApp','false','2021-02-16 01:09:00'),
		('Answer_10','PRJ321_3','MyWebApp.xml in MyWebApp','false','2021-02-16 01:10:00'),
		('Answer_11','PRJ321_3','MyWebApp.xml in MyWebApp/WEB-INF','false','2021-02-16 01:11:00'),
		('Answer_12','PRJ321_3','web.xml in MyWebApp/WEB-INF','true','2021-02-16 01:12:00'),
		('Answer_13','PRJ321_4','Java EE 4','false','2021-02-16 01:13:00'),
		('Answer_14','PRJ321_4','Java EE 3','false','2021-02-16 01:14:00'),
		('Answer_15','PRJ321_4','Java EE 6','false','2021-02-16 01:15:00'),
		('Answer_16','PRJ321_4','Java EE 5','true','2021-02-16 01:16:00'),
		('Answer_17','PRJ321_5','HttpServletRequest and ServletContext respectively','false','2021-02-16 01:17:00'),
		('Answer_18','PRJ321_5','ServletRequest and ServletConfig respectively','false','2021-02-16 01:18:00'),
		('Answer_19','PRJ321_5','HttpServletRequest and PageContext respectively','false','2021-02-16 01:19:00'),
		('Answer_20','PRJ321_5','ServletRequest and PageContext respectively','true','2021-02-16 01:20:00'),
		('Answer_21','PRJ321_6','name','false','2021-02-16 01:21:00'),
		('Answer_22','PRJ321_6','localhost','false','2021-02-16 01:22:00'),
		('Answer_23','PRJ321_6','id','false','2021-02-16 01:23:00'),
		('Answer_24','PRJ321_6','action','true','2021-02-16 01:24:00'),
		('Answer_25','PRJ321_7','GET','false','2021-02-16 01:25:00'),
		('Answer_26','PRJ321_7','HEAD','false','2021-02-16 01:26:00'),
		('Answer_27','PRJ321_7','FORM','false','2021-02-16 01:27:00'),
		('Answer_28','PRJ321_7','POST','true','2021-02-16 01:28:00'),
		('Answer_29','PRJ321_8','doPut','false','2021-02-16 01:29:00'),
		('Answer_30','PRJ321_8','doPost','false','2021-02-16 01:30:00'),
		('Answer_31','PRJ321_8','doHead','false','2021-02-16 01:31:00'),
		('Answer_32','PRJ321_8','doGet','true','2021-02-16 01:32:00'),
		('Answer_33','PRJ321_9','DNS','false','2021-02-16 01:33:00'),
		('Answer_34','PRJ321_9','IP','false','2021-02-16 01:34:00'),
		('Answer_35','PRJ321_9','computer name','false','2021-02-16 01:35:00'),
		('Answer_36','PRJ321_9','localhost','true','2021-02-16 01:36:00'),
		('Answer_37','PRJ321_10','get of Session','false','2021-02-16 01:37:00'),
		('Answer_38','PRJ321_10','getValues of HttpSession','false','2021-02-16 01:38:00'),
		('Answer_39','PRJ321_10','getAttribute of Session','false','2021-02-16 01:39:00'),
		('Answer_40','PRJ321_10','getAttribute of HttpSession','true','2021-02-16 01:40:00'),
		('Answer_41','PRJ321_11','Servlet','false','2021-02-16 01:41:00'),
		('Answer_42','PRJ321_11','JSP','false','2021-02-16 01:42:00'),
		('Answer_43','PRJ321_11','EJB','false','2021-02-16 01:43:00'),
		('Answer_44','PRJ321_11','JavaMail','true','2021-02-16 01:44:00'),
		('Answer_45','PRJ321_12','actions','false','2021-02-16 01:45:00'),
		('Answer_46','PRJ321_12','name of a jsp file','false','2021-02-16 01:46:00'),
		('Answer_47','PRJ321_12','tag libraries','false','2021-02-16 01:47:00'),
		('Answer_48','PRJ321_12','scriptlets','true','2021-02-16 01:48:00'),
		('Answer_49','PRJ321_13','EJB','false','2021-02-16 01:49:00'),
		('Answer_50','PRJ321_13','JSM','false','2021-02-16 01:50:00'),
		('Answer_51','PRJ321_13','MOM','false','2021-02-16 01:51:00'),
		('Answer_52','PRJ321_13','Servlet','true','2021-02-16 01:52:00'),
		('Answer_53','PRJ321_14','\jars','false','2021-02-16 01:53:00'),
		('Answer_54','PRJ321_14','\WEB-INF','false','2021-02-16 01:54:00'),
		('Answer_55','PRJ321_14','\WEB-INF\classes','false','2021-02-16 01:55:00'),
		('Answer_56','PRJ321_14','\WEB-INF\lib','true','2021-02-16 01:56:00'),
		('Answer_57','PRJ321_15','getStream','false','2021-02-16 01:57:00'),
		('Answer_58','PRJ321_15','getBinaryStream','false','2021-02-16 01:58:00'),
		('Answer_59','PRJ321_15','getWriter','false','2021-02-16 01:59:00'),
		('Answer_60','PRJ321_15','getOutputStream','true','2021-02-16 02:00:00')