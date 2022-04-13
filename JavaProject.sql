CREATE DATABASE JavaProject
GO
USE JavaProject
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE UserTable 
(
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(50) NOT NULL,
	UserPassword NVARCHAR(50) NOT NULL,
	IsAdmin INT NOT NULL
)
GO

CREATE TABLE Movie 
(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(100),
	PublishedDate NVARCHAR(100),
	MovieDescription NVARCHAR(max),
	OriginalTitle NVARCHAR(100),
	MovieLength INT,
	PicturePath NVARCHAR(200),
	Link NVARCHAR(200),
	ReleaseDate NVARCHAR(100)
)
GO

CREATE TABLE Genre 
(
	IDGenre INT PRIMARY KEY IDENTITY,
	GenreName NVARCHAR(50) NOT NULL
)
GO

CREATE TABLE MovieGenre 
(
	IDMovieGenre INT PRIMARY KEY IDENTITY,
	MovieID INT FOREIGN KEY REFERENCES Movie(IDMovie),
	GenreID INT FOREIGN KEY REFERENCES Genre(IDGenre)
)
GO

CREATE TABLE Person 
(
	IDPerson INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(50),
	LastName NVARCHAR(50)
)
GO

CREATE TABLE MovieActor 
(
	IDMovieActor INT PRIMARY KEY IDENTITY,
	MovieID INT FOREIGN KEY REFERENCES Movie(IDMovie), 
    PersonID INT FOREIGN KEY REFERENCES Person(IDPerson)
)
GO

CREATE TABLE MovieDirector 
(
	IDMovieDirector INT PRIMARY KEY IDENTITY,
	MovieID INT FOREIGN KEY REFERENCES Movie(IDMovie), 
    PersonID INT FOREIGN KEY REFERENCES Person(IDPerson)
)
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE PROC createUser
	@Username NVARCHAR(50),
	@UserPassword NVARCHAR(50),
	@IsAdmin int,
	@Result int output
AS
BEGIN
	IF EXISTS (SELECT * FROM UserTable WHERE Username = @Username)
	BEGIN
		SET @Result = 0
	END
	ELSE
	BEGIN
		INSERT INTO UserTable (Username, UserPassword, IsAdmin) 
			VALUES (@Username, @UserPassword, @IsAdmin)
		SET @Result = 1
	END
END
GO

CREATE PROC verifyUser
	@Username NVARCHAR(50),
	@UserPassword NVARCHAR(50)
AS
BEGIN
	IF((SELECT COUNT(*) FROM UserTable WHERE Username = @Username AND UserPassword = @UserPassword) IS NOT NULL)
	BEGIN
		SELECT * FROM UserTable 
		WHERE Username = @Username AND UserPassword = @UserPassword
	END
END
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE PROC createMovie
	@Title NVARCHAR(100),
	@PublishedDate NVARCHAR(100),
	@MovieDescription NVARCHAR(max),
	@OriginalTitle NVARCHAR(100), 
	@MovieLength int,
	@PicturePath NVARCHAR(200),
	@Link NVARCHAR(200),
	@ReleaseDate NVARCHAR(100),
	@Id int OUTPUT
AS
BEGIN
	INSERT INTO Movie(Title,PublishedDate, MovieDescription, OriginalTitle, MovieLength, PicturePath, Link, ReleaseDate) 
		VALUES (@Title, @PublishedDate, @MovieDescription, @OriginalTitle, @MovieLength, @PicturePath, @Link, @ReleaseDate)
	SET @Id= SCOPE_IDENTITY()
END
GO

CREATE PROC selectMovie
	@IdMovie INT
AS
BEGIN
	SELECT * FROM Movie 
	WHERE IDMovie = @IDMovie
END
GO

CREATE PROC selectMovies
AS
BEGIN
	SELECT * FROM Movie
END
GO

CREATE PROCEDURE updateMovie
	@Title NVARCHAR(100),
	@PublishedDate NVARCHAR(100),
	@MovieDescription NVARCHAR(max),
	@OriginalTitle NVARCHAR(100), 
	@MovieLength int,
	@PicturePath NVARCHAR(200),
	@Link NVARCHAR(200),
	@ReleaseDate NVARCHAR(100),
	@IdMovie INT
AS 
BEGIN 
	UPDATE Movie 
	SET 
		Title = @Title, 
		PublishedDate = @PublishedDate, 
		MovieDescription = @MovieDescription,
		OriginalTitle = @OriginalTitle, 
		MovieLength = @MovieLength, 
		PicturePath = @PicturePath, 
		Link = @Link, 
		ReleaseDate = @ReleaseDate				
	WHERE 
		IDMovie = @IdMovie
END
GO

CREATE PROC deleteMovie
	@IdMovie INT
AS
BEGIN
	DELETE 
	FROM MovieGenre 
	WHERE MovieGenre.MovieID = @IdMovie

	DELETE 
	FROM MovieActor 
	WHERE MovieActor.MovieID = @IdMovie

	DELETE 
	FROM MovieDirector 
	WHERE MovieDirector.MovieID = @IdMovie

	DELETE 
	FROM Movie 
	WHERE IDMovie = @IdMovie
END
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE PROC getOrCreateGenreId
	@GenreName NVARCHAR(50),
	@IdGenre INT OUTPUT
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Genre WHERE GenreName = @GenreName)
	BEGIN
		INSERT INTO  Genre (GenreName)
			VALUES (@GenreName)
		SET @IdGenre = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @IdGenre = IDGenre FROM Genre WHERE GenreName = @GenreName
	END
END
GO

CREATE PROC createMovieGenres
	@MovieId int,
	@GenreId int
AS
BEGIN
	INSERT INTO MovieGenre(MovieID, GenreID)
		VALUES (@MovieId, @GenreId)
END
GO

CREATE PROC updateGenre
	@GenreName NVARCHAR(50),
	@IdGenre int
AS
BEGIN
	UPDATE Genre
	SET GenreName = @GenreName WHERE IDGenre = @IdGenre
END
GO

CREATE PROC selectGenre
	@IdGenre int
AS
BEGIN
	SELECT * FROM Genre 
	WHERE IDGenre = @IdGenre
END
GO

CREATE PROC selectGenres
AS
BEGIN
	SELECT * FROM Genre
END
GO

CREATE PROC selectMovieGenres
	@IdMovie int
AS
BEGIN
	SELECT * FROM MovieGenre as mg 
	INNER JOIN Genre as g ON g.IDGenre = mg.GenreID 
	WHERE MovieID = @IdMovie
END
GO

CREATE PROC deleteMovieGenres
	@IdMovie int
AS
BEGIN
	DELETE FROM MovieGenre 
	WHERE MovieGenre.MovieID = @IdMovie
END
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE PROC createMovieActors
	@MovieId int,
	@ActorId int
AS
BEGIN
	INSERT INTO MovieActor (MovieID, PersonID)
		VALUES (@MovieId, @ActorId)
END
GO

CREATE PROC createMovieDirectors
	@MovieId int,
	@DirectorId int
AS
BEGIN
	INSERT INTO MovieDirector (MovieID, PersonID)
		VALUES (@MovieId, @DirectorId)
END
GO

CREATE PROC selectPerson
	@IdPerson int
AS
BEGIN
	SELECT * FROM Person 
	WHERE IDPerson = @IdPerson
END
GO

CREATE PROC selectPeople
AS
BEGIN
	SELECT * FROM Person
END
GO

CREATE PROC selectMovieActors
	@IdMovie int
AS
BEGIN
	SELECT * FROM MovieActor as ma 
	INNER JOIN Person as p ON ma.PersonID = p.IDPerson 
	WHERE MovieID = @IdMovie
END
GO

CREATE PROC selectMovieDirectors
	@IdMovie int
AS
BEGIN
	SELECT * FROM MovieDirector as md 
	INNER JOIN Person as p ON md.PersonID = p.IDPerson 
	WHERE MovieID = @IdMovie
END
GO

CREATE PROC getOrCreatePersonId
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@IdPerson INT OUTPUT
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Person WHERE FirstName = @FirstName AND LastName = @LastName)
	BEGIN
		INSERT INTO Person (FirstName, LastName)
			VALUES (@FirstName, @LastName)
	SET @IdPerson = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @IdPerson = IDPerson FROM Person 
		WHERE FirstName = @FirstName AND LastName = @LastName
	END
END
GO

CREATE PROC updatePerson
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@IdPerson int
AS
BEGIN
	UPDATE Person
	SET FirstName = @FirstName, LastName = @LastName WHERE IDPerson = @IdPerson
END
GO

CREATE PROC deletePerson
	@IdPerson int
AS
BEGIN
	DELETE 
	FROM MovieActor 
	WHERE PersonID = @IdPerson

	DELETE 
	FROM MovieDirector 
	WHERE PersonID = @IdPerson

	DELETE 
	FROM Person 
	WHERE IDPerson = @IdPerson
END
GO

CREATE PROC deleteMovieActors
	@IdMovie int
AS
BEGIN
	DELETE 
	FROM MovieActor 
	WHERE MovieActor.MovieID = @IdMovie
END
GO

CREATE PROC deleteMovieDirectors
	@IdMovie int
AS
BEGIN
	DELETE 
	FROM MovieDirector 
	WHERE MovieDirector.MovieID = @IdMovie
END
GO

/*------------------------------------------------------------------------------------------------------------------------------------------------*/

CREATE PROC deleteAll
AS 
BEGIN 
	DELETE FROM MovieActor
	DELETE FROM MovieDirector
	DELETE FROM Person
	DELETE FROM MovieGenre
	DELETE FROM Genre
	DELETE FROM Movie
END
GO

DECLARE @Result int
EXEC createUser 'admin','admin', 1, @Result
PRINT @Result
GO

