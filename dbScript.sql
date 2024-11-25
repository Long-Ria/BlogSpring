USE [master]
GO
/****** Object:  Database [Project_PRN231]    Script Date: 11/25/2024 3:57:10 PM ******/
CREATE DATABASE [Project_PRN231]

GO
ALTER DATABASE [Project_PRN231] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Project_PRN231].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Project_PRN231] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Project_PRN231] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Project_PRN231] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Project_PRN231] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Project_PRN231] SET ARITHABORT OFF 
GO
ALTER DATABASE [Project_PRN231] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Project_PRN231] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Project_PRN231] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Project_PRN231] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Project_PRN231] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Project_PRN231] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Project_PRN231] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Project_PRN231] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Project_PRN231] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Project_PRN231] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Project_PRN231] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Project_PRN231] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Project_PRN231] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Project_PRN231] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Project_PRN231] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Project_PRN231] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Project_PRN231] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Project_PRN231] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Project_PRN231] SET  MULTI_USER 
GO
ALTER DATABASE [Project_PRN231] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Project_PRN231] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Project_PRN231] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Project_PRN231] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Project_PRN231] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Project_PRN231] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [Project_PRN231] SET QUERY_STORE = ON
GO
ALTER DATABASE [Project_PRN231] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Project_PRN231]
GO
/****** Object:  Table [dbo].[Answers]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answers](
	[AnswerId] [int] IDENTITY(1,1) NOT NULL,
	[QuestionId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[ParentAnswer] [int] NULL,
	[Content] [varchar](max) NULL,
	[CreatedAt] [datetime] NULL,
	[UpdatedAt] [datetime] NULL,
 CONSTRAINT [PK_Answers] PRIMARY KEY CLUSTERED 
(
	[AnswerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[question_tag]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[question_tag](
	[question_id] [int] NOT NULL,
	[tag_id] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Questions]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Questions](
	[QuestionId] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](255) NULL,
	[Content] [varchar](max) NULL,
	[UserId] [int] NULL,
	[CreatedDate] [datetime] NULL,
	[Status] [bit] NULL,
 CONSTRAINT [PK_Questions] PRIMARY KEY CLUSTERED 
(
	[QuestionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[QuestionTag]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[QuestionTag](
	[QuestionId] [int] NULL,
	[TagId] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[RoleId] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](255) NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[RoleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tags]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tags](
	[TagId] [int] IDENTITY(1,1) NOT NULL,
	[TagName] [varchar](255) NULL,
 CONSTRAINT [PK_Tags] PRIMARY KEY CLUSTERED 
(
	[TagId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 11/25/2024 3:57:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserId] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](255) NULL,
	[Password] [varchar](255) NULL,
	[Email] [varchar](255) NULL,
	[Gender] [bit] NULL,
	[Avatar] [varchar](max) NULL,
	[RoleId] [int] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Answers] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Answers] ADD  DEFAULT (getdate()) FOR [UpdatedAt]
GO
ALTER TABLE [dbo].[Questions] ADD  CONSTRAINT [DF_Questions_CreatedDate]  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answers_Answers] FOREIGN KEY([ParentAnswer])
REFERENCES [dbo].[Answers] ([AnswerId])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answers_Answers]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answers_Questions] FOREIGN KEY([QuestionId])
REFERENCES [dbo].[Questions] ([QuestionId])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answers_Questions]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answers_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answers_Users]
GO
ALTER TABLE [dbo].[question_tag]  WITH CHECK ADD  CONSTRAINT [FK98t1ucn99srvf809bi2ceahpc] FOREIGN KEY([tag_id])
REFERENCES [dbo].[Tags] ([TagId])
GO
ALTER TABLE [dbo].[question_tag] CHECK CONSTRAINT [FK98t1ucn99srvf809bi2ceahpc]
GO
ALTER TABLE [dbo].[question_tag]  WITH CHECK ADD  CONSTRAINT [FKa0soof5dkct76ysv6b1wp7t01] FOREIGN KEY([question_id])
REFERENCES [dbo].[Questions] ([QuestionId])
GO
ALTER TABLE [dbo].[question_tag] CHECK CONSTRAINT [FKa0soof5dkct76ysv6b1wp7t01]
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD  CONSTRAINT [FK_Questions_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[Questions] CHECK CONSTRAINT [FK_Questions_Users]
GO
ALTER TABLE [dbo].[QuestionTag]  WITH CHECK ADD  CONSTRAINT [FK_QuestionTag_Questions] FOREIGN KEY([QuestionId])
REFERENCES [dbo].[Questions] ([QuestionId])
GO
ALTER TABLE [dbo].[QuestionTag] CHECK CONSTRAINT [FK_QuestionTag_Questions]
GO
ALTER TABLE [dbo].[QuestionTag]  WITH CHECK ADD  CONSTRAINT [FK_QuestionTag_Tags] FOREIGN KEY([TagId])
REFERENCES [dbo].[Tags] ([TagId])
GO
ALTER TABLE [dbo].[QuestionTag] CHECK CONSTRAINT [FK_QuestionTag_Tags]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Roles] ([RoleId])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Roles]
GO
USE [master]
GO
ALTER DATABASE [Project_PRN231] SET  READ_WRITE 
GO
