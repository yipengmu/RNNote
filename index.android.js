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
    Text,DrawerLayoutAndroid,
    View,Navigator,TouchableHighlight,
    } = React;

var API_KEY = '7waqfqbprs7pajbz28mqf6vz';
var API_URL = 'http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json';
var PAGE_SIZE = 25;
var PARAMS = '?apikey=' + API_KEY + '&page_limit=' + PAGE_SIZE;
var REQUEST_URL = API_URL + PARAMS;

var AwesomeProject = React.createClass({
    getInitialState: function () {
        return {
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2,
            }),
            loaded: false,
        };
    },

    componentDidMount: function () {
        this.fetchData();
    },

    fetchData: function () {
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

    render: function () {
        var DrawerLayout = (
            <View style={styles.leftDrawer}>
                <Image source={require('./uilib/img/bg_home_dark.png')} style={{height: 160,justifyContent:'center'}}>

                    <Image source={{uri: 'lm_user_logo_default'}}
                           style={{width: 80, height: 80,marginLeft: 40}}/>
                </Image>
            </View>);

        var Nav = (<DrawerLayoutAndroid
                drawerWidth={300}
                drawerPosition={DrawerLayoutAndroid.positions.Left}
                renderNavigationView={() => DrawerLayout}>
                <View style={styles.main_container}>
                    <ToolbarAndroid style={styles.toolbar}
                                    title={'便利贴'}
                                    navIcon={{uri: "ic_arrow_back_white", isStatic: true}}
                                    titleColor={'#FFFFFF'}/>
                    <ListView
                        dataSource={this.state.dataSource}
                        renderRow={this.renderMovie}
                        style={styles.listView}
                    />

                    <View style={styles.bottom}>

                        <TouchableHighlight onPress={this._onPressButton}>
                            <Image source={require('./uilib/img/icon_create_text.png')}
                                   style={{width: 60, height: 60}}
                                />
                        </TouchableHighlight>
                    </View>
                </View>
            </DrawerLayoutAndroid>
        );
        return Nav;
    },
    _onPressButton(event) {

        setTimeout(function() {
            alert("button me")
        }, 200);

        console.log('Pressed!');
    },
    renderMovie:function(movie){
        return (
            <TouchableHighlight onPress={() => this._pressRow(movie.id)}>
                <View style={styles.container} key={movie.id}>
                    <Image
                        source={{uri:movie.posters.thumbnail}}
                        style={styles.thumbnail} />
                    <View style={styles.rightContainer}>
                        <Text style={styles.title} numberOfLines={1}>
                            {movie.title}
                        </Text>
                        <Text style={styles.year}>
                            {movie.year}
                        </Text>
                    </View>
                    <View style={styles.separator}></View>
                </View>
            </TouchableHighlight>
        );
    },

    _pressRow:function(rowID: number){
        this.props.navigator.push({
            title:'详情',
            component:MovieView
        })
    }
});


var TextNoteCreatePage = React.createClass({
    onPressFeed() {
        this.props.navigator.push({name: 'textNoteCreatePage'});
    },


    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.welcome} onPress={this.onPressFeed} >
                    textNoteCreatePage  This is welcome view.Tap to go to feed view.
                </Text>
            </View>
        );
    }

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
    bottom: {
        height: 50,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: "#eeeeee"
    }, leftDrawer: {
        backgroundColor: "#fff",
        flex:1
    }
});

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);
