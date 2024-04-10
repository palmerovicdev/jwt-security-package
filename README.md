# JWT-Security-Package

This package is a security package that provides a JWT authentication system for your Spring-Boot application. It is a simple package that allows 
you to 
authenticate users using JWT tokens. It also provides a middleware that you can use to protect your routes.

## Use Case

This package is useful when you want to authenticate users in your Spring-Boot application using JWT tokens. It is also useful when you want to protect your routes using JWT tokens.

## Installation

To use this package, you only need to clone the repo to your local machine and import it as a module in your project.

## Usage

To use this package, you need to create a configuration class that implements the DataSourceConfig interface. This class contains a method that returns the DataSource object that you will use to connect to your database.

Here is an example of a configuration class that implements the DataSourceConfig interface: 

```java
@Configuration
public class DataSourceConfiguration implements DataSourceConfig {

    @Bean
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
```

## Contributing

If you want to contribute to this project, you can fork the repo and create a pull request with your changes. You can also create an issue if you find any bugs or want to suggest a new feature.