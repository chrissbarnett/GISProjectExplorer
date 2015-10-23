package edu.tufts.gis.projectexplorer.service.solr;

/**
 * Created by cbarne02 on 5/7/15.
 */
public class SolrConfigServiceImpl {

    /*
    id

    project_id String

    title String, text

    key_theme String, text, mv
    key_application String, text, mv
    key_method String, text, mv
    key_place String, text, mv

    geonames_id, String, mv
    reference_url String, mv
    reference_text String, mv

    course_name String
    course_instructor String

    course_semester String
    course_year Date?

    course_id String

    //can be multiple
    student_name_* String
    student_year_* Date
    student_degree_* String
    student_school_* String
    student_department_* String, mv

    paper_title String, text
    paper_url String
    paper_thumb_url String
    paper_format String

    poster_url String
    poster_thumb_url String
    poster_txt text
     */


/*
this is a basic, untouched solr schema

{
  "responseHeader":{
    "status":0,
    "QTime":1},
  "schema":{
    "name":"example",
    "version":1.5,
    "uniqueKey":"id",
    "fieldTypes":[{
        "name":"_bbox_coord",
        "class":"solr.TrieDoubleField",
        "stored":false,
        "docValues":true,
        "precisionStep":"8"},
      {
        "name":"alphaOnlySort",
        "class":"solr.TextField",
        "omitNorms":true,
        "sortMissingLast":true,
        "analyzer":{
          "tokenizer":{
            "class":"solr.KeywordTokenizerFactory"},
          "filters":[{
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.TrimFilterFactory"},
            {
              "class":"solr.PatternReplaceFilterFactory",
              "pattern":"([^a-z])",
              "replace":"all",
              "replacement":""}]}},
      {
        "name":"bbox",
        "class":"solr.BBoxField",
        "geo":"true",
        "numberType":"_bbox_coord",
        "distanceUnits":"kilometers"},
      {
        "name":"binary",
        "class":"solr.BinaryField"},
      {
        "name":"boolean",
        "class":"solr.BoolField",
        "sortMissingLast":true},
      {
        "name":"currency",
        "class":"solr.CurrencyField",
        "currencyConfig":"currency.xml",
        "defaultCurrency":"USD",
        "precisionStep":"8"},
      {
        "name":"date",
        "class":"solr.TrieDateField",
        "positionIncrementGap":"0",
        "precisionStep":"0"},
      {
        "name":"double",
        "class":"solr.TrieDoubleField",
        "positionIncrementGap":"0",
        "precisionStep":"0"},
      {
        "name":"float",
        "class":"solr.TrieFloatField",
        "positionIncrementGap":"0",
        "precisionStep":"0"},
      {
        "name":"ignored",
        "class":"solr.StrField",
        "indexed":false,
        "stored":false,
        "multiValued":true},
      {
        "name":"int",
        "class":"solr.TrieIntField",
        "positionIncrementGap":"0",
        "precisionStep":"0"},
      {
        "name":"location",
        "class":"solr.LatLonType",
        "subFieldSuffix":"_coordinate"},
      {
        "name":"location_rpt",
        "class":"solr.SpatialRecursivePrefixTreeFieldType",
        "geo":"true",
        "maxDistErr":"0.001",
        "distErrPct":"0.025",
        "distanceUnits":"kilometers"},
      {
        "name":"long",
        "class":"solr.TrieLongField",
        "positionIncrementGap":"0",
        "precisionStep":"0"},
      {
        "name":"lowercase",
        "class":"solr.TextField",
        "positionIncrementGap":"100",
        "analyzer":{
          "tokenizer":{
            "class":"solr.KeywordTokenizerFactory"},
          "filters":[{
              "class":"solr.LowerCaseFilterFactory"}]}},
      {
        "name":"point",
        "class":"solr.PointType",
        "subFieldSuffix":"_d",
        "dimension":"2"},
      {
        "name":"random",
        "class":"solr.RandomSortField",
        "indexed":true},
      {
        "name":"string",
        "class":"solr.StrField",
        "sortMissingLast":true},
      {
        "name":"tdate",
        "class":"solr.TrieDateField",
        "positionIncrementGap":"0",
        "precisionStep":"6"},
      {
        "name":"tdouble",
        "class":"solr.TrieDoubleField",
        "positionIncrementGap":"0",
        "precisionStep":"8"},
      {
        "name":"text_en",
        "class":"solr.TextField",
        "positionIncrementGap":"100",
        "indexAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.StopFilterFactory",
              "words":"lang/stopwords_en.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.EnglishPossessiveFilterFactory"},
            {
              "class":"solr.KeywordMarkerFilterFactory",
              "protected":"protwords.txt"},
            {
              "class":"solr.PorterStemFilterFactory"}]},
        "queryAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.SynonymFilterFactory",
              "expand":"true",
              "ignoreCase":"true",
              "synonyms":"synonyms.txt"},
            {
              "class":"solr.StopFilterFactory",
              "words":"lang/stopwords_en.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.EnglishPossessiveFilterFactory"},
            {
              "class":"solr.KeywordMarkerFilterFactory",
              "protected":"protwords.txt"},
            {
              "class":"solr.PorterStemFilterFactory"}]}},
      {
        "name":"text_en_splitting",
        "class":"solr.TextField",
        "autoGeneratePhraseQueries":"true",
        "positionIncrementGap":"100",
        "indexAnalyzer":{
          "tokenizer":{
            "class":"solr.WhitespaceTokenizerFactory"},
          "filters":[{
              "class":"solr.StopFilterFactory",
              "words":"lang/stopwords_en.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.WordDelimiterFilterFactory",
              "catenateNumbers":"1",
              "generateNumberParts":"1",
              "splitOnCaseChange":"1",
              "generateWordParts":"1",
              "catenateAll":"0",
              "catenateWords":"1"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.KeywordMarkerFilterFactory",
              "protected":"protwords.txt"},
            {
              "class":"solr.PorterStemFilterFactory"}]},
        "queryAnalyzer":{
          "tokenizer":{
            "class":"solr.WhitespaceTokenizerFactory"},
          "filters":[{
              "class":"solr.SynonymFilterFactory",
              "expand":"true",
              "ignoreCase":"true",
              "synonyms":"synonyms.txt"},
            {
              "class":"solr.StopFilterFactory",
              "words":"lang/stopwords_en.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.WordDelimiterFilterFactory",
              "catenateNumbers":"0",
              "generateNumberParts":"1",
              "splitOnCaseChange":"1",
              "generateWordParts":"1",
              "catenateAll":"0",
              "catenateWords":"0"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.KeywordMarkerFilterFactory",
              "protected":"protwords.txt"},
            {
              "class":"solr.PorterStemFilterFactory"}]}},
      {
        "name":"text_en_splitting_tight",
        "class":"solr.TextField",
        "autoGeneratePhraseQueries":"true",
        "positionIncrementGap":"100",
        "analyzer":{
          "tokenizer":{
            "class":"solr.WhitespaceTokenizerFactory"},
          "filters":[{
              "class":"solr.SynonymFilterFactory",
              "expand":"false",
              "ignoreCase":"true",
              "synonyms":"synonyms.txt"},
            {
              "class":"solr.StopFilterFactory",
              "words":"lang/stopwords_en.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.WordDelimiterFilterFactory",
              "catenateNumbers":"1",
              "generateNumberParts":"0",
              "generateWordParts":"0",
              "catenateAll":"0",
              "catenateWords":"1"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.KeywordMarkerFilterFactory",
              "protected":"protwords.txt"},
            {
              "class":"solr.EnglishMinimalStemFilterFactory"},
            {
              "class":"solr.RemoveDuplicatesTokenFilterFactory"}]}},
      {
        "name":"text_general",
        "class":"solr.TextField",
        "positionIncrementGap":"100",
        "indexAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.StopFilterFactory",
              "words":"stopwords.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.LowerCaseFilterFactory"}]},
        "queryAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.StopFilterFactory",
              "words":"stopwords.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.SynonymFilterFactory",
              "expand":"true",
              "ignoreCase":"true",
              "synonyms":"synonyms.txt"},
            {
              "class":"solr.LowerCaseFilterFactory"}]}},
      {
        "name":"text_general_rev",
        "class":"solr.TextField",
        "positionIncrementGap":"100",
        "indexAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.StopFilterFactory",
              "words":"stopwords.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.LowerCaseFilterFactory"},
            {
              "class":"solr.ReversedWildcardFilterFactory",
              "maxPosQuestion":"2",
              "maxFractionAsterisk":"0.33",
              "maxPosAsterisk":"3",
              "withOriginal":"true"}]},
        "queryAnalyzer":{
          "tokenizer":{
            "class":"solr.StandardTokenizerFactory"},
          "filters":[{
              "class":"solr.SynonymFilterFactory",
              "expand":"true",
              "ignoreCase":"true",
              "synonyms":"synonyms.txt"},
            {
              "class":"solr.StopFilterFactory",
              "words":"stopwords.txt",
              "ignoreCase":"true"},
            {
              "class":"solr.LowerCaseFilterFactory"}]}},
      {
        "name":"text_ws",
        "class":"solr.TextField",
        "positionIncrementGap":"100",
        "analyzer":{
          "tokenizer":{
            "class":"solr.WhitespaceTokenizerFactory"}}},
      {
        "name":"tfloat",
        "class":"solr.TrieFloatField",
        "positionIncrementGap":"0",
        "precisionStep":"8"},
      {
        "name":"tint",
        "class":"solr.TrieIntField",
        "positionIncrementGap":"0",
        "precisionStep":"8"},
      {
        "name":"tlong",
        "class":"solr.TrieLongField",
        "positionIncrementGap":"0",
        "precisionStep":"8"}],
    "fields":[{
        "name":"_root_",
        "type":"string",
        "indexed":true,
        "stored":false},
      {
        "name":"_version_",
        "type":"long",
        "indexed":true,
        "stored":true},
      {
        "name":"id",
        "type":"string",
        "multiValued":false,
        "indexed":true,
        "required":true,
        "stored":true}],
    "dynamicFields":[{
        "name":"*_coordinate",
        "type":"tdouble",
        "indexed":true,
        "stored":false},
      {
        "name":"ignored_*",
        "type":"ignored",
        "multiValued":true},
      {
        "name":"random_*",
        "type":"random"},
      {
        "name":"attr_*",
        "type":"text_general",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_txt",
        "type":"text_general",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_dts",
        "type":"date",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_tdt",
        "type":"tdate",
        "indexed":true,
        "stored":true},
      {
        "name":"*_is",
        "type":"int",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_ss",
        "type":"string",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_ls",
        "type":"long",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_en",
        "type":"text_en",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_bs",
        "type":"boolean",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_fs",
        "type":"float",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_ds",
        "type":"double",
        "multiValued":true,
        "indexed":true,
        "stored":true},
      {
        "name":"*_dt",
        "type":"date",
        "indexed":true,
        "stored":true},
      {
        "name":"*_ti",
        "type":"tint",
        "indexed":true,
        "stored":true},
      {
        "name":"*_tl",
        "type":"tlong",
        "indexed":true,
        "stored":true},
      {
        "name":"*_tf",
        "type":"tfloat",
        "indexed":true,
        "stored":true},
      {
        "name":"*_td",
        "type":"tdouble",
        "indexed":true,
        "stored":true},
      {
        "name":"*_i",
        "type":"int",
        "indexed":true,
        "stored":true},
      {
        "name":"*_s",
        "type":"string",
        "indexed":true,
        "stored":true},
      {
        "name":"*_l",
        "type":"long",
        "indexed":true,
        "stored":true},
      {
        "name":"*_t",
        "type":"text_general",
        "indexed":true,
        "stored":true},
      {
        "name":"*_b",
        "type":"boolean",
        "indexed":true,
        "stored":true},
      {
        "name":"*_f",
        "type":"float",
        "indexed":true,
        "stored":true},
      {
        "name":"*_d",
        "type":"double",
        "indexed":true,
        "stored":true},
      {
        "name":"*_p",
        "type":"location",
        "indexed":true,
        "stored":true},
      {
        "name":"*_c",
        "type":"currency",
        "indexed":true,
        "stored":true}],
    "copyFields":[]}}
 */

}