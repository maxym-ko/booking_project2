package com.maxym.booking.db.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static DBManager dbManager;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (dbManager == null) dbManager = new DBManager();
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context) initContext.lookup("java:/comp/env");

            // ST4DB - the name of data source
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/booking2");

//            dataSource = (DataSource) initContext.lookup("jdbc/booking2");
            connection = dataSource.getConnection();
        } catch (NamingException ex) {
            ex.printStackTrace();
//            TODO: Catch exception
        }
        return connection;
    }

    public void commitAndClose(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

    public void rollbackAndClose(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

//    public void insertUser(User user) {
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_INSERT_USER,
//                     Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.executeUpdate();
//
//            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
//                if (resultSet.next()) {
//                    user.setId(resultSet.getLong(1));
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    public List<User> findAllUsers() {
//        List<User> users = new ArrayList<>();
//
//        try (Connection connection = getConnection(CONNECTION_URL);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(SQLConstants.SQL_FIND_ALL_USERS)) {
//
//            while (resultSet.next()) {
//                users.add(mapUser(resultSet));
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return users;
//    }
//
//    private User mapUser(ResultSet rs) throws SQLException {
//        long id = rs.getLong(SQLConstants.ID);
//        String login = rs.getString(SQLConstants.USER_LOGIN);
//
//        User user = User.createUser(login);
//        user.setId(id);
//        return user;
//    }
//
//    public void insertTeam(Team team) {
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_INSERT_TEAM,
//                     Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, team.getName());
//            preparedStatement.executeUpdate();
//
//            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
//                if (resultSet.next()) {
//                    team.setId(resultSet.getLong(1));
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    public List<Team> findAllTeams() {
//        List<Team> teams = new ArrayList<>();
//
//        try (Connection connection = getConnection(CONNECTION_URL);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(SQLConstants.SQL_FIND_ALL_TEAMS)) {
//
//            while (resultSet.next()) {
//                teams.add(mapTeam(resultSet));
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return teams;
//    }
//
//    private Team mapTeam(ResultSet rs) throws SQLException {
//        long id = rs.getLong(SQLConstants.ID);
//        String name = rs.getString(SQLConstants.TEAM_NAME);
//
//        Team team = Team.createTeam(name);
//        team.setId(id);
//        return team;
//    }
//
//    public User getUser(String userLogin) {
//        User user = null;
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_FIND_USER_BY_LOGIN)) {
//            preparedStatement.setString(1, userLogin);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    user = mapUser(resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return user;
//    }
//
//    public Team getTeam(String teamName) {
//        Team team = null;
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_FIND_TEAM_BY_NAME)) {
//            preparedStatement.setString(1, teamName);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    team = mapTeam(resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return team;
//    }
//
//    public void setTeamsForUser(User user, Team... teams) {
//        Connection connection = null;
//        try {
//            connection = getConnection(CONNECTION_URL);
//            connection.setAutoCommit(false);
//
//            for (Team team : teams) {
//                addUser2Team(connection, user, team);
//            }
//
//            connection.commit();
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//            try {
//                if (connection != null) connection.rollback();
//            } catch (SQLException e2) {
//                LOGGER.severe(e2.getMessage());
//            }
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                LOGGER.severe(e.getMessage());
//            }
//        }
//    }
//
//    private void addUser2Team(Connection connection, User user, Team team) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_SET_TEAMS_FOR_USER)) {
//            preparedStatement.setLong(1, user.getId());
//            preparedStatement.setLong(2, team.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    public List<Team> getUserTeams(User user) {
//        List<Team> teams = new ArrayList<>();
//
//        try (Connection connection = getConnection(CONNECTION_URL);
//             Statement statement = connection.createStatement();
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_FIND_TEAMS_BY_USER)) {
//            preparedStatement.setLong(1, user.getId());
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    long teamID = resultSet.getLong("team_id");
//                    teams.add(getTeam(teamID));
//                }
//            }
//
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return teams;
//    }
//
//    private Team getTeam(long id) {
//        Team team = null;
//
//        try (Connection connection = getConnection(CONNECTION_URL);
//             Statement statement = connection.createStatement();
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_FIND_TEAM_BY_ID)) {
//            preparedStatement.setLong(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    team = Team.createTeam(resultSet.getString(SQLConstants.TEAM_NAME));
//                    team.setId(id);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//        return team;
//    }
//
//    public void deleteTeam(Team team) {
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_DELETE_TEAM)) {
//            preparedStatement.setLong(1, team.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    public void updateTeam(Team team) {
//        try (Connection connection = getConnection(CONNECTION_URL);
//             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_UPDATE_TEAM)) {
//            preparedStatement.setString(1, team.getName());
//            preparedStatement.setLong(2, team.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    private void truncateTable(String sql) {
//        try (Connection connection = getConnection(CONNECTION_URL);
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            LOGGER.severe(e.getMessage());
//        }
//    }
//
//    public void truncateUsers() {
//        truncateTable(SQLConstants.SQL_TRUNCATE_USERS);
//    }
//
//    public void truncateTeams() {
//        truncateTable(SQLConstants.SQL_TRUNCATE_TEAMS);
//    }
//
//    public void truncateUsersTeams() {
//        truncateTable(SQLConstants.SQL_TRUNCATE_USERS_TEAMS);
//    }
}
