/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". Below, "root" is the start rule.
 *
 * For more information, see the parsers reading.
 */
@skip whitespace{
    expr ::= plus | multiply | constant | var| '(' expr ')';
    plus ::= ((multiply | '(' expr ')' | constant | var) '+')+ (multiply | '(' expr ')' | constant | var);
    multiply ::= (('(' expr ')' | constant | var) '*')+ ('(' expr ')' | constant | var);
}
constant ::= [0-9]+ ('.' [0-9]*)? (('e' | 'E') '-'? [0-9]+)? | '.' [0-9]+ (('e' | 'E') '-'? [0-9]+)?;
var ::= [a-zA-Z]+;

whitespace ::= [ ]+;
