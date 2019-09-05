import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:ice_cola_for_anilist/entity/data.dart';
import 'package:ice_cola_for_anilist/entity/user.dart';

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => HomeState();
}

class HomeState extends State<Home> {
  final _user = new User();
  final _newActivities = <Activity>[];
  final _popList = <MediaData>[]; // Trending Anime & Manga
  final _forumAvtivities = <Forum>[]; // Forum Activity
  final _newReviews = <Review>[]; // Recent Reviews
  final _newAddedAnimeList = <Anime>[]; // Newly Added Anime
  final _newAddedMangaList = <MediaData>[]; // Newly Added Manga

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Title"),
      )
    );
  }
}