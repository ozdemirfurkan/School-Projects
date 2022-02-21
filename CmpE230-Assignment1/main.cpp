#include <iostream>
#include <fstream>
#include <unordered_map>
#include <stack>
#include <vector>

using namespace std;

//when there is a error, this variable become false.
bool noError = true;
//keeps variables
unordered_map<string, int> variables;
/*
 * main string includes variable allocation
 * extra string includes if condition, while loops, print and choose functions etc.
 */
string mainString = "";
string extraString = "";

/*
 * count : count of temporary variables
 * condition_count : count of if, while and choose
 */
int count = 0;
int condition_count = 0;

/*
 * this functions detects whether the variable is new or already allocated
 */
string isNewVariable(string variable){

    while(variable[0] == ' ' || variable[0] == '\t'){

        variable = variable.substr(1);
    }

    while(variable[variable.length() - 1] == ' '){

        variable = variable.substr(0,variable.length() - 1);
    }

    for(int i=0; i<variable.length(); i++){

        if(!((variable[i] <= 'Z' && variable[i] >= 'A') || (variable[i] <= 'z' && variable[i] >= 'a') || (variable[i] <= '9' && variable[i] >= '0'))){

            noError = false;
        }
    }

    if(variable[0] != '%'){

        variable = "%" + variable;
    }

    try{

        variables.at(variable);

    }catch(...){

        variables[variable] = 0;
        mainString += "\t"+ variable +"= alloca i32\n";
        mainString += "\tstore i32 0, i32* " + variable + "\n";

    }

    return variable;
}

/*
 * hasPrecedence function detects precedence for infix to postfix operation
 */
bool hasPrecedence(char a, char b){

    if(b == '+' || b == '-' || b == '('){

        return true;

    }else if(a == '*' || a == '/'){

        return true;

    }else{

        return false;
    }
}

/*
 * this function takes infix expressions and returns postfix expressions
 */
string infixToPostfix(string expression){

    try {

        stack<char> s;
        string postfix = ",";

        for (int i = 0; i < expression.length(); i++) {

            if (((expression[i] <= 'Z' && expression[i] >= 'A') || (expression[i] <= 'z' && expression[i] >= 'a') ||
                 (expression[i] <= '9' && expression[i] >= '0'))) {

                postfix += expression[i];

            } else if (expression[i] == ' ') {

                if (postfix[postfix.length() - 1] != ',') {

                    postfix += ',';
                }

            } else if (expression[i] == '(') {

                s.push(expression[i]);

            } else if (expression[i] == ')') {

                if(s.empty()){

                    throw 505;
                }

                while (s.top() != '(' && (!s.empty())) {

                    if (postfix[postfix.length() - 1] != ',') {

                        postfix += ',';
                    }
                    postfix += s.top();
                    s.pop();

                    if(s.empty()){

                        throw 505;
                    }
                }
                if (s.top() == '(') {

                    s.pop();
                }


            } else if (expression[i] == '+' || expression[i] == '-' || expression[i] == '*' || expression[i] == '/') {

                if (postfix[postfix.length() - 1] != ',') {

                    postfix += ',';
                }

                if (s.empty()) {

                    s.push(expression[i]);

                } else {

                    if (hasPrecedence(expression[i], s.top())) {

                        s.push(expression[i]);

                    } else {

                        while ((!s.empty()) && !(hasPrecedence(expression[i], s.top()))) {

                            postfix += s.top();
                            postfix += ',';
                            s.pop();
                        }

                        s.push(expression[i]);
                    }
                }
            }
        }

        while (!s.empty()) {

            if (postfix[postfix.length() - 1] != ',') {

                postfix += ',';

            }
            postfix += s.top();
            postfix += ',';
            s.pop();
        }

        if (postfix[postfix.length() - 1] != ',') {

            postfix += ',';

        }

        return postfix;

    }catch (...){

        noError = false;
        return "";
    }
}

/*
 * this function evaluate expressions that include choose function
 */
string evaluateExpression(string expression){

    try {

        if (expression.find("choose") != -1) {

            isNewVariable("chres");
            string new_expression = expression.substr(expression.find("choose") + 6, expression.length());
            stack<char> parantheses;
            bool b = true;
            int last_index = 0;

            if(new_expression.find("choose") != -1){

                throw 505;
            }

            while (!parantheses.empty() || b) {

                if (new_expression[last_index] == '(') {

                    parantheses.push('(');
                    b = false;

                } else if (new_expression[last_index] == ')') {

                    parantheses.pop();
                }

                last_index += 1;
            }

            string choose_expression = expression.substr(expression.find("choose") + 6, last_index);
            extraString += "\tbr label %choose" + to_string(condition_count) + "\n";
            extraString += "choose" + to_string(condition_count) + ":\n";
            string condition = choose_expression.substr(choose_expression.find('(') + 1,
                                                        choose_expression.find(',') - 1);
            string result = evaluateExpression(condition);
            if (result[1] == 't' && result[2] == 'm' && result[3] == 'p') {
                extraString += "\t%tmp" + to_string(count) + " = icmp sgt i32 " + result + ", 0\n";
            } else {
                extraString += "\t%tmp" + to_string(count) + " = load i32* " + result + "\n";
                count++;
                extraString += "\t%tmp" + to_string(count) + " = icmp sgt i32 %tmp" + to_string(count - 1) + ", 0\n";
            }
            count++;
            extraString +=
                    "\t br i1 %tmp" + to_string(count - 1) + ", label %end_positive" + to_string(condition_count) +
                    ", label %equal" + to_string(condition_count) + "\n";
            extraString += "equal" + to_string(condition_count) + ":\n";
            extraString += "\t%tmp" + to_string(count) + " = icmp eq i32 %tmp" + to_string(count - 2) + ", 0\n";
            count++;
            extraString += "\t br i1 %tmp" + to_string(count - 1) + ", label %end_equal" + to_string(condition_count) +
                           ", label %end_negative" + to_string(condition_count) + "\n";
            extraString += "end_equal" + to_string(condition_count) + ":\n";
            choose_expression = choose_expression.substr(choose_expression.find(',') + 1, choose_expression.length());
            condition = choose_expression.substr(0, choose_expression.find(','));
            result = evaluateExpression(condition);
            if ((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%') {
                extraString += "\tstore i32 " + result + ", i32* %chres\n";
            } else {
                extraString += "\t%tmp" + to_string(count) + " = load i32* " + result + "\n";
                count++;
                extraString += "\tstore i32 %tmp" + to_string(count - 1) + ", i32* %chres\n";
            }
            extraString += "\tbr label %continue" + to_string(condition_count) + "\n";
            extraString += "end_positive" + to_string(condition_count) + ":\n";
            choose_expression = choose_expression.substr(choose_expression.find(',') + 1, choose_expression.length());
            condition = choose_expression.substr(0, choose_expression.find(','));
            result = evaluateExpression(condition);
            if ((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%') {
                extraString += "\tstore i32 " + result + ", i32* %chres\n";
            } else {
                extraString += "\t%tmp" + to_string(count) + " = load i32* " + result + "\n";
                count++;
                extraString += "\tstore i32 %tmp" + to_string(count - 1) + ", i32* %chres\n";
            }
            extraString += "\tbr label %continue" + to_string(condition_count) + "\n";
            extraString += "end_negative" + to_string(condition_count) + ":\n";
            choose_expression = choose_expression.substr(choose_expression.find(',') + 1, choose_expression.length());
            condition = choose_expression.substr(0, choose_expression.rfind(')'));
            result = evaluateExpression(condition);
            if ((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%') {
                extraString += "\tstore i32 " + result + ", i32* %chres\n";
            } else {
                extraString += "\t%tmp" + to_string(count) + " = load i32* " + result + "\n";
                count++;
                extraString += "\tstore i32 %tmp" + to_string(count - 1) + ", i32* %chres\n";
            }

            extraString += "\tbr label %continue" + to_string(condition_count) + "\n";
            extraString += "continue" + to_string(condition_count) + ":\n";
            condition_count++;
            string start = expression.substr(0, expression.find("choose"));
            expression = expression.substr(0, expression.find("choose")) + "chres" +
                         expression.substr(last_index + 7 + start.length(), expression.length());

        }

        string postfix = infixToPostfix(expression);
        postfix = postfix.substr(1, postfix.length());

        string temp_postfix = postfix;
        int position = 0;
        string token;
        stack<string> result;


        while ((position = temp_postfix.find(',')) != string::npos) {
            token = temp_postfix.substr(0, position);
            temp_postfix.erase(0, position + 1);
            if (token == "+" || token == "-" || token == "*" || token == "/") {

                string var1 = result.top();
                result.pop();
                string var2 = result.top();
                result.pop();
                if (var1[0] == '%') {

                    if (!(var1[1] == 't' && var1[2] == 'm' && var1[3] == 'p')) {

                        extraString += "\t%tmp" + to_string(count) + " = load i32* " + var1 + "\n";
                        var1 = "%tmp" + to_string(count);
                        count++;
                    }
                }

                if (var2[0] == '%') {

                    if (!(var2[1] == 't' && var2[2] == 'm' && var2[3] == 'p')) {

                        extraString += "\t%tmp" + to_string(count) + " = load i32* " + var2 + "\n";
                        var2 = "%tmp" + to_string(count);
                        count++;
                    }
                }

                if (token == "+") {

                    extraString += "\t%tmp" + to_string(count) + " = add i32 " + var2 + ", " + var1 + "\n";
                    result.push("%tmp" + to_string(count));
                    count++;

                } else if (token == "-") {

                    extraString += "\t%tmp" + to_string(count) + " = sub i32 " + var2 + ", " + var1 + "\n";
                    result.push("%tmp" + to_string(count));
                    count++;

                } else if (token == "*") {

                    extraString += "\t%tmp" + to_string(count) + " = mul i32 " + var2 + ", " + var1 + "\n";
                    result.push("%tmp" + to_string(count));
                    count++;

                } else {

                    extraString += "\t%tmp" + to_string(count) + " = sdiv i32 " + var2 + ", " + var1 + "\n";
                    result.push("%tmp" + to_string(count));
                    count++;
                }

            } else {

                if (token[0] >= '0' && token[0] <= '9') {

                    result.push(token);

                } else {

                    token = isNewVariable(token);
                    result.push(token);
                }
            }
        }

        return result.top();

    }catch (...){

        noError = false;
        return "";
    }
}

int main(int argc, char const *argv[]) {

    /*
     * infile: input file
     * outfile : output file
     */
    ifstream infile;
    infile.open(argv[1]);
    vector<string> all_args;
    if (argc > 1) {
        all_args.assign(argv + 1, argv + argc);
    }
    string output_name = all_args[0].substr(0,all_args[0].length()-2) + "ll";
    ofstream outfile;
    outfile.open(output_name);


    string line = "";
    int line_number = 0;

    /*
     * takes lines in input file one by one
     */
    while(getline(infile,line) && noError){

        // for comments
        if(line.find('#') != -1){

            line = line.substr(0,line.find('#'));
        }

        // for assignment
        if(line.find('=') != -1){

            string variable = line.substr(0,line.find('='));
            variable = isNewVariable(variable);
            string expression = line.substr(line.find('=')+1, line.length());
            string result = evaluateExpression(expression);
            if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                count ++;
                extraString += "\tstore i32 %tmp" + to_string(count-1) + ", i32* " + variable + "\n";

            }else{

                extraString += "\tstore i32 " + result + ", i32* " + variable + "\n";
            }

        // for print function
        }else if(line.find("print") != -1){

            string expression = line.substr(line.find('(') , line.rfind(')'));
            string result = evaluateExpression(expression);
            if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 %tmp" + to_string(count) + " )\n";
                count ++;

            }else{

                extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 " + result + " )\n";
            }

        // for if statements
        }else if(line.find("if") != -1){

            if(line.find('(') == -1 || line.find(')') == -1){

                noError = false;
            }
            string expression = line.substr(line.find('(') , line.rfind(')'));
            string if_condition_count = to_string(condition_count);
            extraString += "\tbr label %ifcond" + if_condition_count + "\n";
            extraString += "ifcond" + if_condition_count + ":\n";
            string result = evaluateExpression(expression);
            if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){
                extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                count++;
            }

            extraString += "\t%tmp"+ to_string(count) + " = icmp ne i32 %tmp" + to_string(count-1)+", 0\n";
            extraString += "\tbr i1 %tmp" + to_string(count) +", label %ifbody" + if_condition_count + ", label %ifend" + if_condition_count + "\n";
            count++;
            extraString += "ifbody" + if_condition_count + ":\n";

            while(getline(infile,line) && noError){

                line_number ++;

                if(line.find('#') != -1){

                    line = line.substr(0,line.find('#'));
                }
                if(line.find('}') != -1){
                    break;
                }
                if(line.find('=') != -1){

                    string variable = line.substr(0,line.find('='));
                    variable = isNewVariable(variable);
                    expression = line.substr(line.find('=')+1, line.length());
                    result = evaluateExpression(expression);
                    if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                        extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                        count ++;
                        extraString += "\tstore i32 %tmp" + to_string(count-1) + ", i32* " + variable + "\n";

                    }else{

                        extraString += "\tstore i32 " + result + ", i32* " + variable + "\n";
                    }

                }else if(line.find("print") != -1){

                    expression = line.substr(line.find('(') , line.rfind(')'));
                    result = evaluateExpression(expression);
                    if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                        extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                        extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 %tmp" + to_string(count) + " )\n";
                        count ++;

                    }else{

                        extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 " + result + " )\n";
                    }

                }else if(line.find("if") != -1 || line.find("while") != -1){

                    noError = false;
                }
            }
            if(line.find('}') == -1){
                noError = false;
            }
            extraString += "\tbr label %ifend" + if_condition_count + "\n";
            extraString += "ifend" + if_condition_count + ":\n";
            condition_count ++;

        // for while loops
        }else if(line.find("while") != -1){

            if(line.find('(') == -1 || line.find(')') == -1){

                noError = false;
            }
            string expression = line.substr(line.find('(') , line.rfind(')'));
            string while_condition_count = to_string(condition_count);
            extraString += "\tbr label %whcond" + while_condition_count + "\n";
            extraString += "whcond" + while_condition_count + ":\n";
            string result = evaluateExpression(expression);
            if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){
                extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                count++;
            }

            extraString += "\t%tmp"+ to_string(count) + " = icmp ne i32 %tmp" + to_string(count-1)+", 0\n";
            extraString += "\tbr i1 %tmp" + to_string(count) +", label %whbody" + while_condition_count + ", label %whend" + while_condition_count + "\n";
            count++;
            extraString += "whbody" + while_condition_count + ":\n";

            while(getline(infile,line) && noError){

                line_number++;

                if(line.find('#') != -1){
                    line = line.substr(0,line.find('#'));
                }
                if(line.find('}') != -1){
                    break;
                }
                if(line.find('=') != -1){

                    string variable = line.substr(0,line.find('='));
                    variable = isNewVariable(variable);
                    expression = line.substr(line.find('=')+1, line.length());
                    result = evaluateExpression(expression);
                    if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                        extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                        count ++;
                        extraString += "\tstore i32 %tmp" + to_string(count-1) + ", i32* " + variable + "\n";

                    }else{

                        extraString += "\tstore i32 " + result + ", i32* " + variable + "\n";
                    }

                }else if(line.find("print") != -1){

                    expression = line.substr(line.find('(') , line.rfind(')'));
                    result = evaluateExpression(expression);
                    if(!((result[1] == 't' && result[2] == 'm' && result[3] == 'p') || result[0] != '%')){

                        extraString += "\t%tmp"+ to_string(count) +" = load i32* "+ result + "\n";
                        extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 %tmp" + to_string(count) + " )\n";
                        count ++;

                    }else{

                        extraString += "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([4x i8]* @print.str, i32 0, i32 0), i32 " + result + " )\n";
                    }

                }else if(line.find("if") != -1 || line.find("while") != -1){

                    noError = false;
                }
            }

            extraString += "\tbr label %whcond" + while_condition_count + "\n";
            extraString += "whend" + while_condition_count + ":\n";
            condition_count ++;

        // for syntax errors
        }else if(line != "" && line != "\r" && line != " " ){

            noError = false;
        }

        line_number ++;

    }

    /*
     * if there is no syntax error writes mainString and extraString
     * into output file, else prints syntax error
     */
    if(noError){

        outfile << "; ModuleID = 'mylang2ir'" << endl;
        outfile << "declare i32 @printf(i8*, ...)" << endl;
        outfile << "@print.str = constant [4 x i8] c\"%d\\0A\\00\"" << endl;
        outfile << "\ndefine i32 @main() {" << endl;
        outfile << mainString << endl;
        outfile << extraString << endl;

    }else{

        outfile << "; ModuleID = 'mylang2ir'" << endl;
        outfile << "declare i32 @printf(i8*, ...)" << endl;
        outfile << "@print.str = constant [23 x i8] c\"Line %d: syntax error\\0A\\00\"" << endl;
        outfile << "\ndefine i32 @main() {" << endl;
        outfile << "\tcall i32 (i8*, ...)* @printf(i8* getelementptr ([23x i8]* @print.str, i32 0, i32 0), i32 " + to_string(line_number - 1) + " )" << endl;

    }

    outfile << "\tret i32 0" << endl;
    outfile << "}" << endl;
    infile.close();
    outfile.close();
    return 0;
}