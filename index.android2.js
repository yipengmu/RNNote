/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');

var ToolbarAndroid = require('ToolbarAndroid');

var {
  AppRegistry,
  Image,
  ListView,
  StyleSheet,
  Text,
  View,
} = React;

var API_KEY = '7waqfqbprs7pajbz28mqf6vz';
var API_URL = 'http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json';
var PAGE_SIZE = 25;
var PARAMS = '?apikey=' + API_KEY + '&page_limit=' + PAGE_SIZE;
var REQUEST_URL = API_URL + PARAMS;

var AwesomeProject = React.createClass({
  getInitialState: function() {
    return {
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 !== row2,
      }),
      loaded: false,
    };
  },

  componentDidMount: function() {
    this.fetchData();
  },

  fetchData: function() {
    fetch(REQUEST_URL)
      .then((response) => response.json())
      .then((responseData) => {
        this.setState({
          dataSource: this.state.dataSource.cloneWithRows(responseData.movies),
          loaded: true,
        });
      })
      .done();
  },

  render: function() {
    //
    // if (!this.state.loaded) {
    //   return this.renderLoadingView();
    // }

    return (
      <View style={styles.main_container}>
        <ToolbarAndroid style={styles.toolbar}
                        title={'jilu'}
                        navIcon={{uri: "ic_arrow_back_white", isStatic: true}}
                        titleColor={'#FFFFFF'}/>

      	<View style={styles.header}>
        	<Image source={require('./android/app/src/main/res/mipmap-hdpi/ic_launcher.png')} style={{width: 30, height: 30}}  />
        	<Text > 记录 </Text>
        	<Image source={{uri: 'rnnote'}} style={{width: 30, height: 30}} />
        </View>
       <ListView
    		dataSource={this.state.dataSource}
    		renderRow={this.renderMovie}
    		style={styles.listView}
    		/>
      </View>
    );
  },

  renderLoadingView: function() {
    return (
      <View style={styles.container}>
        <Text>
          Loading movies...
        </Text>
      </View>
    );
  },

  renderMovie: function(movie) {
    return (
      <View style={styles.container}>
        <Image
          source={{uri: movie.posters.thumbnail}}
          style={styles.thumbnail}
        />
        <View style={styles.rightContainer}>
          <Text style={styles.title}>{movie.title}</Text>
          <Text style={styles.year}>{movie.year}</Text>
        </View>
      </View>
    );
  },
});

var styles = StyleSheet.create({
  main_container: {
    flex: 1
  }
  ,
  container: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  toolbar: {
    height: 56,
    backgroundColor: '#FF6600'
  },
  rightContainer: {
    flex: 1,
  },
  title: {
    fontSize: 20,
    marginBottom: 8,
    textAlign: 'center',
  },
  year: {
    textAlign: 'center',
  },
  thumbnail: {
    width: 53,
    height: 81,
  },
  listView: {
    paddingTop: 20,
    backgroundColor: '#F5FCFF',
  },
  header:{
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems : 'center',
    backgroundColor: '#527F34',
  }
});

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);
