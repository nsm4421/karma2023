export type SearchType = "TITLE" | "CONTENT" | "HASHTAG" | "USER" | null;
export type SortField = "TITLE" | null;
export type Direction = "ASC" | "DESC" | null;
export type Article = {
  id:Number;
  title:String;
  content:String;
  hashtags:Set<String>
  createdAt:string
  createdBy:string,
  modifiedAt:string
} 